package forthcafe;

import forthcafe.config.kafka.KafkaProcessor;
import forthcafe.external.DeliveryCancel;
import forthcafe.external.DeliveryService;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    PayRepository payRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    // OrderCancelled 이벤트 처리기(kafka)
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_(@Payload OrderCancelled orderCancelled){

        if(orderCancelled.isMe()){
            System.out.println("##### listener  : " + orderCancelled.toJson());

            List<Pay> list = payRepository.findByMenuId(orderCancelled.getMenuId());
            
            for(Pay pay : list){
            	pay.setStatus("payCancelled");
                payRepository.save(pay);

                // req/res 패턴 처리 
                PayCancelled payCancelled = new PayCancelled();
                BeanUtils.copyProperties(pay, payCancelled);
                payCancelled.publish();

                DeliveryCancel deliveryCancel = new DeliveryCancel();
                BeanUtils.copyProperties(pay, deliveryCancel);

                // feignclient 호출
                PayApplication.applicationContext.getBean(DeliveryService.class).deliveryCancel(deliveryCancel);
            }            
        }
    }

}
