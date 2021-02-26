package forthcafe;

import forthcafe.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MyPageViewHandler {


    @Autowired
    private MyPageRepository myPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_CREATE_1 (@Payload Ordered ordered) {
        try {
            if (ordered.isMe()) {
                // view 객체 생성
                MyPage myPage  = new MyPage();

                // view 객체에 이벤트의 Value 를 set 함
                myPage.setId(ordered.getId());
                myPage.setMenuId(ordered.getMenuId());
                myPage.setMenuName(ordered.getMenuName());
                myPage.setOrdererName(ordered.getOrdererName());
                myPage.setPrice(ordered.getPrice());
                myPage.setQuantity(ordered.getQuantity());
                myPage.setStatus("Ordered");

                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayed_then_UPDATE_1(@Payload Payed payed) {
        try {
            if (payed.isMe()) {
                // view 객체 조회
                Optional<MyPage> Optional = myPageRepository.findById(payed.getId());
                if( Optional.isPresent()) {
                    MyPage myPage = Optional.get();

                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setId(payed.getId());
                    myPage.setMenuId(payed.getMenuId());
                    myPage.setMenuName(payed.getMenuName());
                    myPage.setOrdererName(payed.getOrdererName());
                    myPage.setPrice(payed.getPrice());
                    myPage.setQuantity(payed.getQuantity());
                    myPage.setStatus("payed");

                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayed_then_UPDATE_2(@Payload PayCancelled payCancelled) {
        try {
            if (payCancelled.isMe()) {
                // view 객체 조회
                Optional<MyPage> Optional = myPageRepository.findById(payCancelled.getId());
                if( Optional.isPresent()) {
                    MyPage myPage = Optional.get();
                    
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setId(payCancelled.getId());
                    myPage.setMenuId(payCancelled.getMenuId());
                    myPage.setMenuName(payCancelled.getMenuName());
                    myPage.setOrdererName(payCancelled.getOrdererName());
                    myPage.setPrice(payCancelled.getPrice());
                    myPage.setQuantity(payCancelled.getQuantity());
                    myPage.setStatus("payCancelled");

                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayed_then_UPDATE_3(@Payload Deliveried deliveried) {
        try {
            if (deliveried.isMe()) {
                // view 객체 조회
                Optional<MyPage> Optional = myPageRepository.findById(deliveried.getId());
                if( Optional.isPresent()) {
                    MyPage myPage = Optional.get();
                    
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setId(deliveried.getId());
                    myPage.setMenuId(deliveried.getMenuId());
                    myPage.setMenuName(deliveried.getMenuName());
                    myPage.setOrdererName(deliveried.getOrdererName());
                    myPage.setPrice(deliveried.getPrice());
                    myPage.setQuantity(deliveried.getQuantity());
                    myPage.setStatus("deliveried");

                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayed_then_UPDATE_4(@Payload DeliveryCancelled deliveryCancelled) {
        try {
            if (deliveryCancelled.isMe()) {
                // view 객체 조회
                Optional<MyPage> Optional = myPageRepository.findById(deliveryCancelled.getId());
                if( Optional.isPresent()) {
                    MyPage myPage = Optional.get();
                    
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setId(deliveryCancelled.getId());
                    myPage.setMenuId(deliveryCancelled.getMenuId());
                    myPage.setMenuName(deliveryCancelled.getMenuName());
                    myPage.setOrdererName(deliveryCancelled.getOrdererName());
                    myPage.setPrice(deliveryCancelled.getPrice());
                    myPage.setQuantity(deliveryCancelled.getQuantity());
                    myPage.setStatus("deliveryCancelled");

                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayed_then_UPDATE_5(@Payload Ordered ordered) {
        try {
            if (ordered.isMe()) {
                // view 객체 조회
                Optional<MyPage> Optional = myPageRepository.findById(ordered.getId());
                if( Optional.isPresent()) {
                    MyPage myPage = Optional.get();
                    
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setId(ordered.getId());
                    myPage.setMenuId(ordered.getMenuId());
                    myPage.setMenuName(ordered.getMenuName());
                    myPage.setOrdererName(ordered.getOrdererName());
                    myPage.setPrice(ordered.getPrice());
                    myPage.setQuantity(ordered.getQuantity());
                    myPage.setStatus("ordered");

                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPayed_then_UPDATE_6(@Payload OrderCancelled orderCancelled) {
        try {
            if (orderCancelled.isMe()) {
                // view 객체 조회
                Optional<MyPage> Optional = myPageRepository.findById(orderCancelled.getId());
                if( Optional.isPresent()) {
                    MyPage myPage = Optional.get();
                    
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setId(orderCancelled.getId());
                    myPage.setMenuId(orderCancelled.getMenuId());
                    myPage.setMenuName(orderCancelled.getMenuName());
                    myPage.setOrdererName(orderCancelled.getOrdererName());
                    myPage.setPrice(orderCancelled.getPrice());
                    myPage.setQuantity(orderCancelled.getQuantity());
                    myPage.setStatus("orderCancelled");

                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}