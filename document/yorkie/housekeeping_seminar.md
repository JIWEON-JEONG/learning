# Housekeeping

## Definition
- 더이상 필요하지 않은 데이터와 리소스를 주기적으로 정리해주는 백그라운드 서비스.

## 배경
- 메모리 사용 관리, 데이터 저장 최적화를 통해 시스템의 성능과 효율성을 높이기 위해 사용.

## 흐름

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
- MongoDB 컬렉션과 Lease Lock 을 통한 리더십 선출.
  - [관련 PR](https://github.com/yorkie-team/yorkie/pull/1373)
- [원자적 FindOneAndUpdate](https://www.mongodb.com/ko-kr/docs/manual/reference/method/db.collection.findoneandupdate/?utm_source=chatgpt.com) + 고유 싱글턴 문서 + [만료시간 TTL](https://www.mongodb.com/ko-kr/docs/manual/core/index-ttl/?utm_source=chatgpt.com) + 토큰으로 획득/갱신/양도 관리

### 흐름

#### go mutex.lock unlock
> 동시에/경합적으로 호출되는 걸 막고, 공유 상태의 불변식(invariant)을 보존해야 할 때 사용.

~~~
1. Job 중복등록을 막기 위해 Lock 사용
func (r *Yorkie) Start() error {
	r.lock.Lock()
	defer r.lock.Unlock()

	if err := r.RegisterHousekeepingTasks(r.backend); err != nil {
		return err
	}
	...
}

2. 
func (lm *LeadershipManager) Start(ctx context.Context) error {
	lm.mutex.Lock()
	defer lm.mutex.Unlock()

	// Start leadership acquisition loop
	lm.wg.Add(1)
	go lm.leadershipLoop(ctx)
}
~~~

#### 서버 시작 시 
1. server.New() -> backend.New(...)에서 하우스키핑 인스턴스 생성 (Memory)
   - DefaultLeadershipConfig 생성 (renewal 5, lease 15, renewal 마다 현시각 기준 15초 뒤로 밀어두는 방식으로 갱신)
   - 갱신 주기 ≤ Lease의 1/3이 안전한 경험칙 [etcd](https://github.com/etcd-io/etcd/pull/9952)
2. yorkie.Start(...)
   - Housekeeping Tasks Register (두 가지 하우스키핑 잡을 스케줄러에 등록)
     - 커서 기반 배치 순회를 통해 Project 선정. (lastCompactionProjectID)
   - backend.Start() -> housekeeping.Start() -> LeadershipManagerStart()
     - 리더십 매니저를 한 번 기동하고(고루틴 생성), “주기적으로 리더십을 획득/갱신”하는 루프 실행.
     - renewalInterval(5s) 마다 handleLeadershipCycle() 수행.
   - handleLeadershipCycle() 
     - leadership 정보가 메모리에 올라와 있을 경우
       - TryLeadership(), 메모리의 leadership 객체 Store.
       - 작업 실패 시, 메모리의 Leader 데이터 nil 처리를 통해 다음 틱때, 새로운 leader 선출하도록 구성.
     - leadership 정보가 메모리에 없을 경우 (서버 재시작)
       - TryLeadership(), 메모리의 Leader 데이터 및 boolean Store.
   - TryLeadership()
     - 현재 메모리에 leader lease token 이 존재하면 
       - leader token renew. (now + 15s)
     - 존재하지 않으면 
       - schema 의 document expired_at 된 문서가 존재하거나 document 자체가 없을 경우, 새로운 leadership upsert.
       - expired_at 되지 않은 문서가 존재할 경우 return.

#### 리더십(획득/갱신) 전용 루프
> 정상적인 단일 리더를 지속적으로 유지를 목적으로 renewalInterval(5s) 마다 handleLeadershipCycle() 수행.

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
  - 코드가 항상 singleton: 1로만 문서를 만들고/업데이트하므로, 결과적으로 {singleton: 1} 문서는 최대 1개만 존재.
  - findOneAndUpdate(upsert) 패턴과 결합해 원자적 획득/갱신을 안전하게 적용.
- term : 리더십 에폭(epoch)/라운드 번호. 리더가 바뀔 때 증가(갱신 중에는 유지).
    - 워커나 스토리지는 “현재 저장된 epoch”와 요청의 epoch가 같은지 검증.
    - 스플릿 브레인 상황에서 늦게 도착한 옛 리더의 작업(낡은 epoch)은 즉시 거부됨.
    - **리더 전환 구간에서의 중복 실행/정합성 깨짐 방지**


### Q&A
1. Housekeeping 을 실행하게 될 leader node 에서는 yorkie 의 비즈니스적인 요청 (그외의 요청)들에 대한 가중치가 낮아지는가?
~~~
첫번째로 라우팅 관련 문서 (sharded cluster mode.md) 에 관련 내용이 적시되어 있지 않은 부분과,
두번째로는  Yorkie 의 라우팅 규칙이 "요청의 해시 키 + 일관 해싱(consistent hashing)”으로 문서/프로젝트 단위로 특정 서버에 고정 라우팅" 이 핵심이기 때문에 
“저 서버에만 덜 보내자”처럼 임의로 비중을 낮추기는 구조적으로 쉽지 않겠다 라는 생각이 들었습니다.
~~~
2. housekeeping tasks(e.g., client cleanup, document compaction) 부분실패.
~~~
여러 태스크를 독립적으로 수행한다. 어떤 태스크가 실패해도 다른 태스크의 성공분은 유지되고, 실패한 태스크만 다음 주기에서 재시도하는 방식으로 부분실패에 대응.
~~~
3. 리더 선출 실패 시,
~~~
메모리에 leader 정보가 존재한다면, 해당 정보를 nil & false 처리 후, 다음 Tick 때 리더 재선출 로직 (“먼저 성공한 놈이 임자(First-come / first-commit)” 
을 통해 재처리를 통한 실패 관리.
~~~

----

## Document Compaction

### 배경
- 장기간 편집으로 changes / snapshots / version_vectors 히스토리 무한 증가 → 저장공간·성능 저하
- 재생 비용(replay)과 스냅샷 관리 비용 개선.

### 해결
> 현재 최종 상태를 기반으로 “새 초기 변경(initial change)” 를 만들어 히스토리를 리셋하고, 이전 changes/snapshots/version_vectors 는 제거.
- [관련 PR](https://github.com/yorkie-team/yorkie/pull/1241)

### 구현

#### 라운드트립 검증
- 
#### YSON
~~~
배경 : 
1. JSON 타입에는 string, number, boolean, null, array, object 같은 일반 타입이 있지만
반면 CRDT는 Counter, Text(리치 텍스트), Tree, Timestamp, VersionVector 같은 도메인 특화 타입/메타데이터가 필요. 

2. JSON은 키 순서, 공백/개행, 유니코드 이스케이프, 숫자 표기(1 vs 1.0 vs 1e0) 등으로 표현 다양성이 커서, “의미가 같은 문서”라도 바이트가 달라질 수 있음.

3. **“스냅샷이 진실인가?”의 검증이 아니라, 여러 커밋(변경 이력)을 한 개의 초기 커밋으로 합쳐도 문서의 최종 상태가 1비트도 안 바뀌는지를 검증하는 것!**
~~~
- YSON(Yorkie Serialized Object Notation): JSON과 달리 CRDT 고유 타입 정보(예: Counter, Text, Tree, VersionVector 등)를 함께 담아 정확히 복원을 위한 목적.
- 라운드트립 보장: CRDT 내부 상태 → YSON → 문서에 다시 주입(SetYSON)하는 왕복 변환이 가능해서, “현재 상태만으로 새 초기 변경(initial change)을 만드는” 컴팩션 절차가 간단·안전해짐.

### 흐름

#### housekeeping scheduling (30s) 마다 CompactDocuments() 수행 
> CompactDocuments() : lastCompactionProjectID 이후의 프로젝트들을 일정 개수(projectFetchSize)만큼 훑으면서,   
> 각 프로젝트에서 컴팩션 후보 문서를 최대 candidatesLimitPerProject 만큼 뽑아 최소 변경 수(compactionMinChanges) 이상인 문서들 일괄 컴팩션.

1. LockerWithTryLock() 
  - 대기열·병목 방지: mutex.Lock로 기다리게 하면 실행이 줄줄이 쌓여서 전체 지연이 커짐.
  - 리소스 피크 억제: 컴팩션은 IO/CPU 헤비 작업이라 동시 2개 이상 돌면 스토리지 피크.
  - Mutex Lock 을 통해 작업 중 들어오는 Job 을 바로 반환(no-op).
2. FindCompactionCandidates()
  - FindNextNCyclingProjectInfos()
    - lastProjectID 커서를 기반으로 pageSize 개수 만큼 조회.
    - 만약 pageSize 만큼 projects 가 없다면, 부족한 개수만큼 랩어라운드 조회. -> 전체 데이터를 순회하면서 언제나 pageSize 만큼 조회.
  - range FindCompactionCandidatesPerProject() - Compaction Project 후보자 (candidatesLimit 내에서 붙은 클라이언트가 없고 && 변경 수가 임계 이상)
    - (candidatesLimit * 2) 선 조회 후, 조건 부 탈락. 
    > 문제 : 정렬/커서가 없어 항상 “앞쪽 일부”만 보게 되고, 그 일부에서 이미 30개를 채워버리면 뒤쪽은 영원히 기회가 없음. 
    > -> 프로젝트 내부에도 커서가 필요하지 않을까? 
    - 조회 한 documents 들을 순회하며, candidatesLimit / isAttached / compactionMinChanges 조건 부 필터링.
    - return Candidates
3. Candidates 를 순회하며, Housekeeping.CompactDocument() 수행.
  > 병렬로 처리 하지 않은 이유가 있을까?  
  - Connect Document 는 책임지는 문서의 소유/책임 노드 로 내부 RPC 전송. 
  - 하우스키핑 리더는 후보만 고르고, 실제 컴팩션은 그 문서를 담당하는 노드에서 RPC로 실행. 
  - 실패 시 continue -> "언젠간 후보가 되어서 시도 될거야~ 대신 로그는 찍어두자."
4. ClusterRPC.CompactDocument() - RPC Entry point
  - (project-doc) key 기반 mutex Lock 을 통해, 문서 동시 접근 문제 해결.
  - document 실제 compact 수행.
5. compaction.Compact() - 실제 compaction 수행 함수
  - attach client = 0 확인. 
  - BuildInternalDocForServerSeq() : DB의 변경 이력을 ServerSeq 까지 적용해 최종 문서 생성. (Storage 반영하지 않은 상태. 즉 검증되지 않은 상태)
    - Cache 에서 최근 Snapshot 조회 (DeepCopy 를 통한 캐시 오염 방지)
    - 캐시에 Document 가 없거나, 캐시의 ServerSeq 가 더 앞서있는경우 (비교군은 내부 RPC 에서 doc info 를 조회 할때의 serverSeq)
      - Closest Snapshot ServerSeq 기반으로 조회 
      - NewInternalDocumentFromSnapshot() : 베이스 스냅샷을 내부 문서로 복원하고, Lamport/Vector/Checkpoint를 그 시점 맞춘다. // 이유 추가 
    - FindChangesBetweenServerSeqs() : base Seq ~ 목표 Server Seq 까지 changeInfo 조회. 
    - ApplyChangePack()
    - GC 수행 
      - GetMinVersionVector() 를 통해 참여자들(서버·활성/동기화 대상 클라이언트)의 체크포인트를 모아 좌표별 최솟값 조회.
      - GarbageCollect() 를 통해 ≤ MinVV 면 삭제.
    - 캐시 반영, Deep Copy() Return.
  - [YSON 기반 라운드트립 검증](#-라운드트립-검증).
  - 캐시 무효화 
  - CompactChangeInfos() 을 통한 변경 영속화
    1. purgeDocumentInternals(...) : 메모리캐시, ColChanges, ColVersionVectors, ColSnapshots 컬렉션 모두 삭제.
    > 문제 : 삭제 후, Insert 실패시 영구 손상을 초래하지 않나? 
    2. ColChanges.InsertOne(...) : 초기 변경을 changes 컬렉션에 Insert.
    3. ColDocuments.UpdateOne(...) : 조건부 업데이트 (compaction base SEQ) 로 동일한지, 동일하다면 해당 문서의 ServerSEQ, time 업데이트.


#### 최소 버전 벡터(MinVV)
- 의미: “이 문서와 관련된 모든 참여자(서버/붙어있는 클라이언트/아직 동기화될 수 있는 주체)가 최소한 여기까지는 봤다”는 공통 하한선.
- 하우스키핑의 inactive client deactivation 같은 게 MinVV를 끌어올려 GC가 더 진행되도록 도와준다.

### Q&A

1. 스냅샷에 대한 검증은 없을까?

#### 직접적으로 검증에 대한 로직은 없지만, 각 절차 별 간접적으로 검증을 하게 된다.
~~~

1. converter.BytesToSnapshot 으로 스냅샷을 CRDT 루트 객체 + presences로 역직렬화 할 때, 스냅샷 자체에 대한 검증.
2. 적용(Apply) 중 인과/참조 검증 
  - 인과 불일치: 변경의 사전조건(선행 변경) 이 스냅샷 상태와 맞지 않음 (삭제된 요소 재삭제 같은)

3. 체크포인트 전진
  - 한 개씩(순서대로) 리플레이해서 T에 정확히 도달했는가 검증 -> 시나리오 대로, 스냅샷 생성이 진행되었다 검증. 
~~~



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
