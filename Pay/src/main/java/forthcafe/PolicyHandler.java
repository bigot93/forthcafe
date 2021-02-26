package forthcafe;

import forthcafe.config.kafka.KafkaProcessor;

import java.util.List;
import java.util.Optional;

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
            System.out.println("##### OrderCancelled listener  : " + orderCancelled.toJson());

            // view 객체 조회
            Optional<Pay> Optional = payRepository.findById(orderCancelled.getId());

            if( Optional.isPresent()) {
                Pay pay = Optional.get();

                // 객체에 이벤트의 eventDirectValue 를 set 함
                pay.setId(orderCancelled.getId());
                pay.setMenuId(orderCancelled.getMenuId());
                pay.setMenuName(orderCancelled.getMenuName());
                pay.setOrdererName(orderCancelled.getOrdererName());
                pay.setPrice(orderCancelled.getPrice());
                pay.setQuantity(orderCancelled.getQuantity());
                pay.setStatus("payCancelled");

                // 레파지 토리에 save
                payRepository.save(pay);
            }
        }
    }

}
