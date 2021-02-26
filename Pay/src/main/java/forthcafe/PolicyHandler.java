package forthcafe;

import forthcafe.config.kafka.KafkaProcessor;

import java.util.List;

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
    // TODO
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_(@Payload OrderCancelled orderCancelled){

        if(orderCancelled.isMe()){
            System.out.println("##### OrderCancelled listener  : " + orderCancelled.toJson());

            List<Pay> list = payRepository.findByMenuId(orderCancelled.getMenuId());
            
            for(Pay pay : list){
            	pay.setStatus("payCancelled");
                payRepository.save(pay);
            }
        }
    }

}
