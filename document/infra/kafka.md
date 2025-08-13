# Kafka

![kafka_topic_partition.png](../resources/kafka_topic_partition.png)

## 다른 Message Queue 와의 차이점 

먼저 카프카는 분산 스트리밍 플랫폼이다. 데이터의 실시간 처리 및 영구 저장에 장점이 존재한다.

- 메세지들이 휘발되지 않고 영구 저장된다. 
- 여러 컨슈머들이 하나의 메세지를 공유할 수 있다. (Offset 을 통해 관리한다.)
- 메세지의 순서 보장.


## 순서 보장

토픽의 파티션 별 순서보장이 가능하다. 

Offset 을 통해 메세지의 순차적인 고유 ID를 부여하여 순서를 보장할 수 있다. 


