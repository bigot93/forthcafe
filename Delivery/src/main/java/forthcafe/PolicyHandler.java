package forthcafe;

import forthcafe.config.kafka.KafkaProcessor;

import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    // payed event listener
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayed_(@Payload Payed payed){

        if(payed.isMe()){
            System.out.println("##### Payed listener  : " + payed.toJson());

            Delivery delivery = new Delivery();
            delivery.setId(payed.getId());
            delivery.setMenuId(payed.getMenuId());
            delivery.setMenuName(payed.getMenuName());
            delivery.setOrdererName(payed.getOrdererName());
            delivery.setPrice(payed.getPrice());
            delivery.setQuantity(payed.getQuantity());
            delivery.setStatus("Delivery");
            deliveryRepository.save(delivery);
        }
    }

}
