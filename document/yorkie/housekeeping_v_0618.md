# Housekeeping

## origin
> [yorkie_housekeeping_document](https://github.com/yorkie-team/yorkie/blob/main/design/housekeeping.md)

## Definition 
- 더이상 필요하지 않은 데이터와 리소스를 주기적으로 정리해주는 백그라운드 서비스.

## 배경 
- 메모리 사용 관리, 데이터 저장 최적화를 통해 시스템의 성능과 효율성을 높이기 위해 사용한다.

## Goals
- Garbage Collection 을 최적화 하기 위한, 비활성화 클라이언트 자동 비활성화. 
- Old changes 의 Storage 오버헤드를 줄이기 위한, 문서 Compaction 제공.
- 시간이 지나도 성능 및 리소스가 최적으로 활용될수 있도록 유지하는 것을 목표 & 제공.

### Client Deactivation

### Document Compaction

#### 배경 

Storage Overhead 를 감소시키기 위해 Document Compaction 을 한다.

#### 해결

~~~
1. Removing old change history
2. Creating a new initial change that represents the current document state
3. Maintaining document integrity while reducing metadata size
~~~

#### Criteria
1. Document must have at least CompactionMinChanges changes (default: 1000)
2. Document must not be currently attached to any client
3. Document content must remain identical after compaction 
   - 컴팩션을 한 뒤에도 사용자가 보는 문서의 내용이 1바이트도 바뀌면 안 된다는 품질/안전 조건

#### Removing old change history

> Tombstone: CRDT 자료구조에서는 실제 데이터를 물리적으로 지우지않고 삭제 되었다는 마크를 남기는 형식으로 처리되는데, 삭제마크를 Tombstone 이라고 한다.

#### Problem

- 저장공간 팽창 문제 (수많은 변경을 오랜기간 저장하게 될때, 스토리지 비용 증가 문제)
- 로드/재생 비용 증가
~~~

팀 노트 문서 D(텍스트 CRDT).

지난 일주일 동안 변경 50,000건(C1…C50000) 누적.

마지막 스냅샷은 C10,000 시점에 1개만 존재.

사용자 로드 시 새로 접속한 클라이언트는 스냅샷(S10000)을 읽고, 거기에 C10001~C50000 = 40,000건을 재생(replay)해야 현재 상태에 도달.

재생 과정에서 tombstone이 많은 구간은 참조/순서 복원 검사가 추가로 필요 → CPU·메모리 사용 증가, 초기 로드 지연.

~~~
- 동기화 지연: Push/Pull 시 상대의 버전까지 필요한 변경을 골라내는 diff 계산 범위가 커짐 → 레이턴시 증가.
- Tombstone 잔존. 

> 1. 문서가 오래, 자주 편집될수록 미분 가능한 오퍼들의 꼬리가 길어짐(텍스트 삽입/삭제·맵 변경·리스트 이동 등).
> 2. CRDT 특성상 인과관계(버전/시퀀스)를 보존해야 하므로, “그냥 지우기”가 아니라 의미 보존 압축이 필요.


#### 해결 //코드로 Double Check 필요
- Yorkie의 Document Compaction은
“최신 스냅샷만 남기고, 그 이전의 변경/스냅샷/버전 메타데이터는 삭제” 하는 구조.
- 즉, 항상 현재 상태 + 이후 변경만 있으면 되고, 과거 이력은 스토리지 최적화를 위해 삭제.


#### Creating a new initial change that represents the current document state

> 초기 변경(Initial Change):  
빈 상태(empty) → 현재 스냅샷 상태를 “한 번의 변경”으로 재구성한 새 출발점(Genesis).
이후에는 이 초기 변경 이후의 변경들만 히스토리에 쌓이도록 하여, 부팅/동기화/리플레이 비용을 낮춘다.

#### Problem
- 부팅/조인 비용 증가
  - 새 클라이언트가 합류할 때, 과거 변경을 많이 알아야 현재 상태에 도달(리플레이) → 초기 로드 느림.
- 동기화(diff) 비용 증가
- 인과관계(버전/시퀀스/tombstone) 복잡도 증가
  - 긴 히스토리로 인해 병합·검증 경로가 길어지고, tombstone 관리도 무거워짐.

~~~
팀 노트 문서 D(텍스트 CRDT).

현재 상태는 "OKRs Q3 최종안" 텍스트. 변경 80,000건(C1…C80000) 누적.
마지막 스냅샷은 C20,000 시점의 S20000.

새 참여자 로드시: S20000 로드 + C20001~C80000 = 60,000건 리플레이 필요.
문서가 길고 tombstone도 많아, 초기 로드 및 diff 계산이 수 초~수십 초 소요.
~~~


#### 해결 

- 초기 변경(Initial Change) 생성  
  - 현재 스냅샷(S₀)을 기준으로, 빈 문서에 초기 변경 1개를 적용하면 S₀가 그대로 재현되도록 결정적(canonical) 순서로 오퍼를 구성한다.
  - 버전/클록 Rebase
    - epoch/seq/version-vector를 초기 변경 시점으로 초기화 → 이후 변경만 얕게 쌓임.
  - 이후 변경만 유지
    - “빈 상태 + 초기 변경 1회 적용 = 현재 상태” 이므로, 과거 히스토리에 의존하지 않고 바로 부팅 가능.


#### Maintaining document integrity while reducing metadata size
> 무결성 유지 + 메타 축소:  
문서의 “보이는 내용(콘텐츠)”은 완전히 동일하게 보존하면서, 해당 상태를 만들기 위해 필요 없어진 과거 변경/스냅샷/버전 메타를 안전하게 정리(삭제/아카이브)하는 절차.


#### Problem 
- 원본 데이터 손실 문제 
  - 메타데이터를 잘못 지우면 현재 내용을 재현 못 하거나 일부가 사라질 수 있음.

- 동시성 경합
  - 컴팩션 중 누군가가 문서에 attach 하여 변경을 넣으면, 컴팩션 결과와 충돌/유실 가능.

- 부분 실패/중단
  - 중간 단계에서 오류가 나면 “반쯤 정리된” 상태가 되어 복구가 까다로워짐.

#### 해결 
- 안전 조건 & 잠금 
  - Client 가 Attach 되어있지 않은 상태를 조건으로 Compaction 진행.
  - 문서 단위 락 혹은 분산락을 통해 Document 동시성 문제 잠금을 통한 해결.
  - 리더십 수행 (Housekeeping 작업을 수행하는 하나의 리더 인스턴스 지정)

- 기준점 고정 (Safe Point)
  - 최신 스냅샷 확보 (마지막 스냅샷 Sₖ + 그 이후 패킷”을 배치 적용) 후 Rebase.

- **무결성 검증**

> “내용은 1바이트도 안 바뀌었다”를 수학적으로 보장해야 안전한 purge 가능.

  -  실제 어떻게 무결성을 검증하는지에 대해서 알아봐야한다. 

- 원자적 커밋 & 롤백
  - 실패 시 원상복구를 쉽게 하기 위해 수행.
  - 트랜잭션을 통해 구현.

### Performance Characteristics

#### Scheduling and Concurrency
> 매 Interval(기본 30초) 마다 리더 인스턴스가 하우스키핑 스케줄을 한 번 돈다.
>
> 그때 프로젝트 목록을 라운드로빈(커서 기반)으로 훑어서, 이번 라운드에 처리할 최대 ProjectFetchSize 개의 프로젝트를 고른다.
>
> 각 프로젝트마다 최대 CandidatesLimitPerProject 개의 후보 문서를 뽑아 큐에 넣고, 워커들이 문서 단위 락을 잡아 비첨부 + CompactionMinChanges 충족 문서만 컴팩션한다.
>
> 다음 라운드(다음 30초)엔 커서를 이어받아 그다음 프로젝트 구간을 처리한다 → 모든 프로젝트가 공평하게 순환.

### Cluster Mode Considerations

- Only the master server executes housekeeping tasks (leader election)
- Prevents duplicate work across cluster nodes
- Future versions may distribute tasks by project for better scalability

#### 샤드 클러스터 환경에서의 리더 선출

- 하우스키핑은 “마스터(leader) 서버 한 대만” 실행하도록 설계되어 있고, 클러스터에서는 리더 선출로 그 마스터를 뽑아 실행된다.
- 리더 선출 방식은 MongoDB 의 lease lock 방식을 사용한다.

#### Leadership
- 클러스터 안에서 오직 한 노드만 “조정자(오케스트레이터)” 역할을 맡도록 하는 합의.
- 하우스키핑처럼 “한 번만 돌아야 하는” 주기 작업(스케줄)을 중복 없이 실행하기 위함.

~~~
leadership 컬렉션에 하나의 문서로 상태를 보관:

{
"_id": "housekeeping:global",   // 스코프
"holderId": "srv-A",            // 현재 리더 노드
"leaseExpiresAt": "...",         // 리스 만료 시각(서버 시간 기준)
"epoch": 42,                    // 펜싱 토큰(세대 번호)
"updatedAt": "..."
}
~~~
~~~
간단 타임라인 예시

설정: Heartbeat=10초, TTL=30초

t=0: A가 리더 당선(holderId=A, leaseExpiresAt=t+30s)

t=10, 20: A가 갱신(각각 +30s)

t=25: A 장애 발생 → 더 이상 갱신 못 함

t=50: 리스 만료 감지 → B가 선점 성공(holderId=B, epoch=+1) ⇒ 자동 Failover
~~~

#### Split Brain
- 정의: 네트워크 분리/오류로 둘(이상)의 노드가 동시에 자신이 리더라고 착각하는 비정상 상태.
- 문제: 하우스키핑이 중복 스케줄을 뿌리거나, 같은 문서를 두 군데서 동시에 컴팩션 시도 → 데이터/락 충돌.

#### (Fencing token = epoch)의 역할
- 리더가 변경될 때마다 단조증가 되는 수.
- 원리: 
  - 리더가 작업을 큐에 넣을 때마다 epoch를 태그해서 보냄.
  - 워커나 스토리지는 “현재 저장된 epoch”와 요청의 epoch가 같은지 검증.
  - 스플릿 브레인 상황에서 늦게 도착한 옛 리더의 작업(낡은 epoch)은 즉시 거부됨.

- 단 한 순간 두 리더가 존재하더라도, 오래된 리더의 작업이 실행되는 걸 차단 → 안전!

~~~
예시

A(리더, epoch=7)가 작업들 발행 중.

장애로 잠깐 끊김 → B가 takeover, epoch=8로 승격.

A가 회복되어 7번 epoch 작업을 뒤늦게 밀어도, 워커는 “요청 epoch=7 < 현재 epoch=8 → 거부”.
~~~

#### Flow 

스케줄러(1대)
- 라운드로빈으로 프로젝트 선정 → 각 프로젝트에서 후보 문서(!IsAttached && changes ≥ CompactionMinChanges) 뽑아 작업 큐에 넣음.
- 이때 락을 미리 잡진 않음

워커(여러 대, 병렬)

- 큐에서 작업을 pop → 문서 락(TTL+heartbeat) 시도.

- 성공: 컴팩션 수행.

- 실패(이미 잠겨 있음): 그 작업을 지연 재큐잉(가시성 타임아웃/딜레이)하거나 드롭하고, 다음 작업으로 넘어감.

Yorkie는 “잠긴 문서(compaction용 락 충돌/첨부 상태)”를 어떻게 다루나?

> 집계 → 개별 시도 → 실패 시 건너뛰기(skip) 구조예요.   
> 하우스키핑이 프로젝트별 컴팩션 후보 목록을 수집한 뒤, 후보 문서마다 CompactDocument RPC를 호출합니다. 서버는 분산 락을 먼저 획득하고, 문서가 attach(편집 중) 인지도 검사합니다. 
> 둘 중 하나라도 만족하지 못하면 즉시 에러로 반환하고 그 문서는 이번 라운드에서 건너뜁니다. 다음 하우스키핑 주기에 다시 후보로 올라오면 그때 재시도돼요.
>
>이 동작은 PR/설계에 명시된 흐름도와 설명에서 확인돼요:
>
>하우스키핑이 “후보 탐색 + 분산 락 하에서만 컴팩션 수행”을 한다는 점, 그리고 워크플로에서 락 획득 → 첨부 여부 확인 → 상태 재구성/검증 → 성공 시 교체로 진행된다는 점이 나옵니다.
>
>“문서가 attach 되어 있으면 에러를 돌려준다”는 서버 측 분기(시퀀스 다이어그램)가 명시돼 있습니다. 즉, 바쁜 문서에 매달려 스핀하지 않고 다음 후보로 넘어가는 방식이에요.

~~~
하우스키핑이 프로젝트별 컴팩션 후보를 조회

후보마다 CompactDocument(projectID, docID) 호출

서버:

분산 락 획득 시도 → 실패면 에러 반환(스킵)

첨부 여부 검사 → 첨부되어 있으면 에러 반환(스킵)

현재 상태를 YSON/JSON으로 직렬화→ 동일 상태로 재구성→ 내용 동일성 검증

OK면 과거 changes/snapshots/versionvectors 삭제 + 초기 변경으로 치환
~~~

## Question 

### 아래의 옵션을 어떤 기준, 어떻게 정하였을까 
~~~
Housekeeping:
  # Interval between housekeeping runs (default: 30s)
  Interval: "30s"

  # Maximum number of candidates to be returned per project (default: 500)
  CandidatesLimitPerProject: 500

  # Maximum number of projects to be returned in a single run (default: 100)
  ProjectFetchSize: 100

  # Minimum number of changes required to compact a document (default: 1000)
  CompactionMinChanges: 1000
~~~

