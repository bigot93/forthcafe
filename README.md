# forthcafe
# winterone
# 서비스 시나리오
### 기능적 요구사항
1. 고객이 메뉴를 주문한다.
2. 고객이 결재한다
3. 결재가 완료되면 주문 내역을 보낸다
4. 매장에서 메뉴 완성 후 배달을 시작한다
5. 주문 상태를 고객이 조회 할 수 있다
6. 고객이 주문을 쥐소 할 수 있다
7. 결재 취소시 배송이 같이 취소 되어야 한다


### 비기능적 요구사항
1. 트랜젝션
   1. 결재 완료가 되지 않으면 주문이 진행되지 않는다 → Sync 호출
   1. 결재가 취소되면 배달이 진행되지 않는다 → Sync 호출
2. 장애격리
   1. 배송에서 장애가 발송해도 결재와 주문은 24시간 받을 수 있어야 한다 →Async(event-driven), Eventual Consistency
   1. 결재가 과중되면 주문을 받지 않고 잠시 후에 하도록 유도한다 → Circuit breaker, fallback
3. 성능
   1. 고객이 주문상태를 주문내역조회에서 확인할 수 있어야 한다 → CQRS

# Event Storming 결과

![EventStormingV1](https://github.com/bigot93/forthcafe/blob/main/images/eventingstorming_forthcafe.png)
![image](https://user-images.githubusercontent.com/57469176/109598051-d4f50580-7b5b-11eb-9c6c-5091e7120d4e.png)
