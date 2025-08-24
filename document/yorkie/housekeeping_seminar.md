# Housekeeping

## Definition
- 더이상 필요하지 않은 데이터와 리소스를 주기적으로 정리해주는 백그라운드 서비스.

## 배경
- 메모리 사용 관리, 데이터 저장 최적화를 통해 시스템의 성능과 효율성을 높이기 위해 사용.

----

## 샤드 클러스터 환경에서의 리더 선출
### 배경 
- 중복 작업 방지 
  - 여러 노드의 동시에 Stale Client Deactivation 이나 문서 Compaction에 대한 이중 작업 문제
- 스토리지 오버헤드 문제 방지 
  - 다수 노드가 동일 주기(예: 30s)로 후보 스캔·컴팩션·디액티베이션 시 동시 읽기/쓰기 몰림.
  - 후보 스캔이나 대량 스냅샷 기록은 IO 피크.
- 모니터링 * 운영 단순화
  - 여러 노드가 각자 따로 housekeeping 수행 시 관측·추적·롤백이 어려움.

### 해결 
> leader election 을 통해 선출된 리더 1대만 housekeeping 작업 수행.

### 구현 
- MongoDB 의 Lease Lock을 통한 리더십 선출.
  - [관련 PR](https://github.com/yorkie-team/yorkie/pull/1373)
- [원자적 FindOneAndUpdate](https://www.mongodb.com/ko-kr/docs/manual/reference/method/db.collection.findoneandupdate/?utm_source=chatgpt.com) + 고유 싱글턴 문서 + [만료시간 TTL](https://www.mongodb.com/ko-kr/docs/manual/core/index-ttl/?utm_source=chatgpt.com) + 토큰으로 획득/갱신/양도 관리

### 흐름 

#### 서버 시작 시 
1. backend.New(...)에서 하우스키핑 인스턴스를 만들 때 DB 인스턴스와 Hostname을 주입.
2. backend.Start(ctx)가 호출되면 Housekeeping.Start(ctx) → 내부에서 LeadershipManager가 실행.

#### LeadershipManager (주 로직 수행)
~~~
Follower --(TryLeadership("", leaseDur) 성공 & lease.Hostname==me)--> Leader
Leader   --(TryLeadership(token, leaseDur) 실패)------------------> Follower
Leader   --(TryLeadership(token, leaseDur) 성공)------------------> Leader(lease 갱신)
~~~

#### Application Layer
1. 주기(renewalInterval)마다 **database.TryLeadership**를 호출하여 현재 노드의 리더십 상태를 획득/갱신하려고 시도.
2. 리더 여부 판단/상태 전환은 DB 호출 결과에 의존.
3. 성공 시 내부 상태(isLeader, currentLease)를 갱신하고, 실패 시 Follower로 강등.
4. 리더일 때만 하우스키핑 작업들을 실행.
5. 워커는 주기마다 term 확인 → 변동 시 작업 중단(새 리더에게 양도)


#### Database Layer (mongo)
- 원자적 리더 획득/갱신의 모든 판단을 책임진다.
  - 리스 만료 판단(예: expires_at <= now)
  - 토큰 일치 검증(lease_token 비교)
  - 단일 문서 보장(싱글턴 레코드 + 유니크 인덱스)
  - 원자적 FindOneAndUpdate(upsert)로 획득/갱신/term 증가 처리
  
- 호출 결과로 현재 리더십 문서(LeadershipInfo)를 반환.

~~~
type LeadershipInfo struct {
	Hostname   string    `bson:"hostname"`
	LeaseToken string    `bson:"lease_token"`
	ElectedAt  time.Time `bson:"elected_at"`
	ExpiresAt  time.Time `bson:"expires_at"`
	RenewedAt  time.Time `bson:"renewed_at"`
	Term       int64     `bson:"term"`
	Singleton  int       `bson:"singleton"`
}
~~~

- lease_token : 현재 리더만 알고 있는 비밀 토큰. 리더 갱신(renew)은 해당 토큰을 통해 수행.
  - Stale 갱신 방지: 과거에 리더였던 노드가 네트워크 복구 후 갱신을 시도해도 토큰이 다르면 거부.
  - 경쟁 조건/스플릿 브레인 완화: 오직 정당한 소유자만 갱신 가능 (CAS).
  - **term 과 차이점** : 현재 리더만 아는 비밀 토큰을 갱신 조건으로 걸어, 과거 리더나 다른 노드가 renew를 시도해도 불일치로 거절
- singleton: 항상 고정값으로 저장하고, 유니크 인덱스를 걸어 문서가 단 하나만 존재하도록 강제.
  - 동시 생성 경쟁에서도 리더십 문서가 복수로 생기는 일 차단.
  - findOneAndUpdate(upsert) 패턴과 결합해 원자적 획득/갱신을 안전하게 적용.
- term : 리더십 에폭(epoch)/라운드 번호. 리더가 바뀔 때 증가(갱신 중에는 유지).
    - 워커나 스토리지는 “현재 저장된 epoch”와 요청의 epoch가 같은지 검증.
    - 스플릿 브레인 상황에서 늦게 도착한 옛 리더의 작업(낡은 epoch)은 즉시 거부됨.
    - **리더 전환 구간에서의 중복 실행/정합성 깨짐 방지**

----

## Document Compaction

### 배경
- 장기간 편집으로 changes / snapshots / version_vectors 히스토리 무한 증가 → 저장공간·성능 저하

### 해결
> 현재 최종 상태를 기반으로 “새 초기 변경(initial change)” 를 만들어 히스토리를 리셋하고, 이전 changes/snapshots/version_vectors 는 제거.
- [관련 PR](https://github.com/yorkie-team/yorkie/pull/1241)

### 구현

#### 라운드트립 검증
- 
#### 현재 CRDT 상태 스냅샷 생성 (YSON)
- 

### 흐름
#### Application Layer
- RunHousekeepingTick(ctx)
  1. 리더일 때만 실행되는 하우스키핑 틱에서 컴팩션 후보를 조회하고, 각 후보에 대해 CompactDocument를 호출.
- CompactDocument(ctx, docID)
  1. 분산 락 획득(문서 단위)
  2. Attach 클라이언트 없음 재확인(있으면 중단)
  3. 현재 CRDT 상태 스냅샷 생성(구조적 직렬화; 설계 이슈에서는 YSON 언급)
  4. 라운드트립 검증(스냅샷 → 재구성 결과가 현재 상태와 동일해야 함)
  5. db.CompactChangeInfos(...) 호출로 히스토리 원자 치환
  6. 로그/메트릭 기록(후보 수, 성공/실패, 소요시간 등)

#### Database Layer (mongo)
- FindCompactionCandidates(ctx, projectID, limit, minChanges) ([]DocumentID, error)
    - 프로젝트/임계치 기반으로 컴팩션 후보 문서를 찾음.

- IsDocumentAttached(ctx, docID) (bool, error)
  - 부착 여부 확인. 하나라도 붙어 있으면 즉시 중단.

- ReadMaterializedState(ctx, docID) (StateBlob, error) 
  - 현재 CRDT 상태를 컴팩션 검증용으로 읽어옴(스냅샷 또는 재구성).

- CompactChangeInfos(ctx, docID, initialChange, vv, opts) error
  - 원자 치환 단계:
    1. 기존 changes / snapshots / versionvectors 제거
    2. “현재 상태를 의미하는 새 초기 변경(initial change)” 1건 삽입
    3. 관련 메타(serverSeq 리셋, DocInfo.CompactedAt 갱신 등) 일괄 갱신
  - 트랜잭션/원자 업데이트로 중간 불일치 상태 방지.

~~~
리더 선출 진입점:
housekeeping.Start(ctx) → 내부에서 LeadershipManager.Start(ctx) → leadershipLoop() →
tryAcquireLeadership/renewLease → (리더일 때) 하우스키핑 틱 실행

워커에서 term 사용:
틱 시작 시 expectedTerm := CurrentLease().Term 캡처 →
배치마다 Leader(ctx) or CurrentLease()로 term 재확인 → 변동 시 중단

컴팩션 진입점:
RunHousekeepingTick → FindCompactionCandidates → 후보마다 CompactDocument →
내부에서 락/부착검증/YSON검증 → db.CompactChangeInfos(원자치환)

DB 계약 핵심:
TryLeadership: 만료 판단/토큰 검증/term 증가/원자성 보장은 DB 책임
CompactChangeInfos: 한 번에 치환하여 중간 불일치 상태를 만들지 않음
~~~

#### 요약
1. (하우스키핑 틱) 후보 조회 → 프로젝트별 FindCompactionCandidates
2. 후보별 분산 락 → 부착 없음 재확인
3. 현재 상태 스냅샷 생성 + 라운드트립 검증
4. CompactChangeInfos 호출로 changes/snapshots/VV 원자 치환 + compactedAt 갱신
5. 메트릭/로그 기록, 락 해제
6. (다음 틱) 새 후보 계속 처리

## Stale Client Deactivation

> Client Deactivation: 프로젝트/문서에 더 이상 참여하지 않는 클라이언트를 비활성화로 표기하고, 관련 정리(detach·presence clear 등)를 수행하는 절차.
> 
> Stale Client: 장시간 heartbeat/활동이 없는 등 유휴 상태로 간주되는 클라이언트(서버 설정 임계치 초과).
>
> Stale Client Deactivation: 위 조건을 만족하는 후보들을 주기적으로 찾아 비활성화하여, 버전 벡터 최소값(minVV) 전진과 GC 효율을 높임. (비활성 클라이언트가 남아 있으면 tombstone 수거 지연)

### 배경
- minVersionVector(mVV) 정체: 오래된 클라이언트의 버전 정보가 남아 있으면 minVV가 전진하지 못해 tombstone 정리가 막힘
- 스냅샷/presence 누수: 더 이상 쓰지 않는 presence/구독/스냅샷 캐시가 남아 메모리/스토리지 사용량이 증가

### 해결
- 주기적인 비활성화로 문서의 히스토리 정리와 GC 조건 충족을 돕는다.
- [관련 Doc](https://github.com/yorkie-team/yorkie/blob/main/design/garbage-collection.md?utm_source=chatgpt.com)

### 구현
#### Application Layer
- RunHousekeepingTick(ctx)(리더 전용)
  1. 비활성 후보 검색: “마지막 활동 시각 < 임계치” && 클라이언트 상태가 Activated
  2. 후보별 DeactivateClient(ctx, clientRefKey) 호출
  3. 시작 전에 현재 term 스냅샷 캡처 → 배치 중간에 term 변경 시 즉시 중단/양도

- DeactivateClient
  1. 사전 조건 검사(원자적): 해당 클라이언트의 ClientDocInfo에 Attaching/Attached 문서가 0 이어야 함(없어야 비활성화 가능)
  2. 만족하면 상태를 Deactivated로 전이, 문서 detach 및 presence clear(필요 시) 수행
  3. 결과 반환(최신 ClientInfo)

- [관련 PR](https://github.com/yorkie-team/yorkie/pull/1414)

#### Database Layer (Mongo)

- FindStaleClients(project, threshold, limit): 
  - 임계치를 넘긴 후보 조회(프로젝트/배치 한도 적용)
- DeactivateClient(clientRefKey):
  - 필터: status==Activated AND documents[*].status ∉ {Attaching, Attached}
  - 업데이트: status ← Deactivated, deactivatedAt ← now(), 필요 시 presence 정리용 플래그/데이터 갱신
- Detach/Presence 처리 보조: 
  - Detach 시작 시 DocPullKey 락, presence clear를 위한 ChangePack 생성.

~~~
type ClientInfo struct {
  RefKey     string              // 클라이언트 식별자
  Status     string              // Activated | Deactivated
  LastSeen   time.Time           // 활동 시각(후보 판정 기준)
  Documents  map[DocID]ClientDocInfo // 각 문서에 대한 상태
}

type ClientDocInfo struct {
  Status     string // Attaching | Attached (없으면 미첨부)
  // ...
}


type LeadershipInfo struct {
  Hostname   string
  LeaseToken string
  ElectedAt  time.Time
  ExpiresAt  time.Time
  RenewedAt  time.Time
  Term       int64
  Singleton  int
}
~~~

### 흐름

1. (리더) RunHousekeepingTick
2. Stale Client 후보 조회 → DeactivateClient (Attaching/Attached 존재 시 거부) → Detach/Presence 정리(락 사용)
3. Document Compaction 후보 처리(락 → 라운드트립 검증 → 원자 치환)
4. 메트릭/로그 기록, 다음 틱으로 반복
