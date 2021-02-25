package fourthcafe;

import fourthcafe.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCancelled_(@Payload PayCancelled payCancelled){

        if(payCancelled.isMe()){
            System.out.println("##### listener  : " + payCancelled.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCancelled_(@Payload DeliveryCancelled deliveryCancelled){

        if(deliveryCancelled.isMe()){
            System.out.println("##### listener  : " + deliveryCancelled.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveried_(@Payload Deliveried deliveried){

        if(deliveried.isMe()){
            System.out.println("##### listener  : " + deliveried.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayed_(@Payload Payed payed){

        if(payed.isMe()){
            System.out.println("##### listener  : " + payed.toJson());
        }
    }

}
