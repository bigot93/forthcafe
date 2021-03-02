# 운영

## CI/CD


* 소스 가져오기
```
git clone https://github.com/bigot93/forthcafe.git
```

* build 하기
```
cd /forthcafe

cd Order
mvn package 

cd ..
cd Pay
mvn package

cd ..
cd Delivery
mvn package

cd ..
cd gateway
mvn package

cd ..
cd MyPage
mvn package

```

* Azure 레지스트리에 도커 이미지 push, deploy, 서비스생성
```
cd .. 
cd Order
az acr build --registry skteam01 --image skteam01.azurecr.io/order:v1 .
kubectl create deploy order --image=skteam01.azurecr.io/order:v1
kubectl expose deploy order --type=ClusterIP --port=8080

cd .. 
cd Pay
az acr build --registry skteam01 --image skteam01.azurecr.io/pay:v1 .
kubectl create deploy pay --image=skteam01.azurecr.io/pay:v1
kubectl expose deploy pay --type=ClusterIP --port=8080


cd .. 
cd Delivery
az acr build --registry skteam01 --image skteam01.azurecr.io/delivery:v1 .
kubectl create deploy delivery --image=skteam01.azurecr.io/delivery:v1
kubectl expose deploy delivery --type=ClusterIP --port=8080


cd .. 
cd gateway
az acr build --registry skteam01 --image skteam01.azurecr.io/gateway:v1 .
kubectl create deploy gateway --image=skteam01.azurecr.io/gateway:v1
kubectl expose deploy gateway --type=ClusterIP --port=8080

cd .. 
cd MyPage
az acr build --registry skteam01 --image skteam01.azurecr.io/mypage:v1 .
kubectl create deploy mypage --image=skteam01.azurecr.io/mypage:v1
kubectl expose deploy mypage --type=ClusterIP --port=8080

```
![image](https://user-images.githubusercontent.com/5147735/109618535-fe715980-7b7a-11eb-8adc-dcb07c9a46c3.png)


* yml파일 이용한 deploy
```
cd .. 
cd order
az acr build --registry skteam01 --image skteam01.azurecr.io/order:v1 .
kubectl apply -f kubernetes/deployment.yml 

```

* deployment.yml 
```
1. namespace, image 설정
2. env 설정 (config Map) 
3. readiness 설정 (무정지 배포)
4. liveness 설정 (self-healing)
5. resource 설정 (autoscaling)
```

## [deployment.yml  사진 첨부]


## 동기식 호출 / 서킷 브레이킹 / 장애격리
* 서킷 브레이킹 프레임워크의 선택: Spring FeignClient + Hystrix 옵션을 사용하여 구현함
* Order -> Pay 와의 Req/Res 연결에서 요청이 과도한 경우 CirCuit Breaker 통한 격리
* Hystrix 를 설정: 요청처리 쓰레드에서 처리시간이 610 밀리가 넘어서기 시작하여 어느정도 유지되면 CB 회로가 닫히도록 (요청을 빠르게 실패처리, 차단) 설정

```
// Order서비스 application.yml

feign:
  hystrix:
    enabled: true

hystrix:
  command:
    default:
      #execution.isolation.thread.timeoutInMilliseconds: 610
```


```
// Pay 서비스 Pay.java

 @PostPersist
    public void onPostPersist(){
        Payed payed = new Payed();
        BeanUtils.copyProperties(this, payed);
        payed.setStatus("Pay");
        payed.publishAfterCommit();

        try {
                 Thread.currentThread().sleep((long) (400 + Math.random() * 220));
         } catch (InterruptedException e) {
                 e.printStackTrace();
         }
```

* 부하테스터 siege 툴을 통한 서킷 브레이커 동작 확인: 동시사용자 100명 60초 동안 실시
```
kubectl exec -it pod/siege -c siege -n forthcafe -- /bin/bash
siege -c100 -t60S  -v --content-type "application/json" 'http://localhost:8081/orders POST {"memuId":2, "quantity":1}'
```


## [동작 확인 화면 첨부]





## 오토스케일 아웃
* 앞서 서킷 브레이커(CB) 는 시스템을 안정되게 운영할 수 있게 해줬지만 사용자의 요청을 100% 받아들여주지 못했기 때문에 이에 대한 보완책으로 자동화된 확장 기능을 적용하고자 한다.

* delivery서비스 deployment.yml 설정
```
 resources:
            limits:
              cpu: 500m
            requests:
              cpu: 200m
```
* 다시 expose 해준다.

```
kubectl expose deploy delivery --type=ClusterIP --port=8080 -n forthcafe
```

* Delivery서비스에 대한 replica 를 동적으로 늘려주도록 HPA 를 설정한다. 설정은 CPU 사용량이 15프로를 넘어서면 replica 를 10개까지 늘려준다

```
kubectl autoscale deploy delivery --min=1 --max=10 --cpu-percent=15
```

* siege를 활용해서 워크로드를 2분간 걸어준다. (Cloud 내 siege pod에서 부하줄 것)
```
kubectl exec -it pod/siege -c siege -n forthcafe -- /bin/bash
siege -c100 -t60S  -v --content-type "application/json" 'http://localhost:8081/orders POST {"memuId":2, "quantity":1}'

```

* 오토스케일이 어떻게 되고 있는지 모니터링을 걸어둔다
```
kubectl get deploy delivery -w
```



## 무정지 재배포





## ConfigMap
* application.yml 파일에 ${configurl} 설정
```

```


## Self-healing (Liveness Probe)

