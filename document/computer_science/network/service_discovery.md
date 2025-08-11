# Service Discovery

## 배경 및 문제
마이크로서비스 아키텍처가 등장하면서, 각각의 서버들은 통신하기 위한 주소를 관리하는데에 아래와 같은 문제를 겪었다. 

> 서비스 인스턴스가 자동으로 생성 혹은 삭제되어 IP 주소 혹은 포트가 수정됨. 

## 해결
서비스 레지스트리(Service Registry)라는 중앙 저장소를 통해 이 문제를 해결한다.

1. 등록 : 인스턴스가 시작될 경우, 서버는 레지스트리에 IP 와 서비스 name 을 등록한다.
2. 탐색 : 다른 서버는 IP 주소가 아닌 서비스 name 을 레스트리에 질의하여 IP 주소를 찾는다.

## 예시 
![seoltab-architecture.png](../../resources/seoltab-architecture.png)

## Experience (network problem)