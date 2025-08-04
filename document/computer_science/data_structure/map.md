# Map

## Hashmap
- HashMap은 Array 와 각 요소들이 Key-Value 의 구조를 가지고 있는 자료구조이다.
- 각 요소들을 Bucket 이라고 칭한다.
- Key 정수 값을 해시함수를 통해 얻고, 이를 배열 크기로 Modulo 연산을 진행하여 인덱스를 선정하게 된다.

- HashMap Resizing: 자바 기준 75% 찼을때 (Load Factor >= 0.75), 2배 늘리고 해시값 모듈로 연산 재수행해서 다시 정렬시키는 Flow.

 * 해시충돌: Key가 다른데 Hash 가 같을경우, 혹은 Modulo 연산 한 값이 같을경우 해시충돌이 발생한다.
 * 해결 1 : Value 를 Linked List 로 관리. 단, 연결 리스트의 길이가 특정 임계값(기본값 8)을 넘어서면 트리(Red-Black Tree)로 전환하여 성능을 $O(N)$에서 $O(\log N)$ 개선.
 * 해결 2 : 가장 가까운 비어있는 공간을 활용. 조회시에도 근처 공간들 모두 확인해야함. + 삭제시에도 더미값을 넣어줘야함.

- create : O(1). 리사이징이 발생하는 경우 O(N).
- read : O(1). 혹은 O(logN)
- update : O(1). 혹은 O(logN)
- delete : O(1). 혹은 O(logN)

### Concurrent Hashmap
- 기존의 Hashmap 은 멀티쓰레드 환경에서 동시성문제를 보장하지 않는다.
- Hashtable 같은 경우 멀티쓰레드 환경을 제공하지만, Table 전체에 락을 걸기 때문에 성능이 좋지 않다는 이슈가 있었음.

이를 해결하기 위해 Concurrent Hashmap 이 나오게되었다.
배열 자체에 대해 락을 거는것이 아닌, 버킷별 락을 걸어 성능 개선한 자료구조.

- create : O(1). 리사이징이 발생하는 경우 O(N).
- read : O(1). 혹은 O(logN)
- update : O(1). 혹은 O(logN)
- delete : O(1). 혹은 O(logN)

## Sortedmap
- 키(Key)가 항상 정렬된 상태로 유지되는 맵(Map) 자료구조.
- 게임 내 랭킹시스템 등에 활용 가능한 자료구조.
- ConcurrentSkipListMap, TreeMap 등

- create : O(logN). 리사이징이 발생하는 경우 O(N).
- read : O(logN)
- update : O(logN)
- delete : O(logN)