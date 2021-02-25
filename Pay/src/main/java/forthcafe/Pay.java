package forthcafe;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import forthcafe.external.Delivery;
import forthcafe.external.DeliveryService;

import java.util.List;

// aggregate = JPA entity, Value Object
@Entity
@Table(name="Pay_table")
public class Pay {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String ordererName;
    private String menuName;
    private Long menuId;
    private Double price;
    private Integer quantity;
    private String status;


    @PostPersist
    public void onPostPersist(){
        Payed payed = new Payed();
        BeanUtils.copyProperties(this, payed);
        payed.setStatus("Pay");
        payed.publishAfterCommit();

        // delay test시 주석해제
        // try {
        //         Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        // } catch (InterruptedException e) {
        //         e.printStackTrace();
        // }

        // 임시주석처리
        // PayCancelled payCancelled = new PayCancelled();
        // BeanUtils.copyProperties(this, payCancelled);
        // payCancelled.publishAfterCommit();

        // //Following code causes dependency to external APIs
        // // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        // Delivery delivery = new Delivery();
        // // mappings goes here
        // PayApplication.applicationContext.getBean(DeliveryService.class).deliveryCancel(delivery);


    }

    @PostUpdate
    public void onPostUpdate() {
            
        PayCancelled payCancelled = new PayCancelled();
        BeanUtils.copyProperties(this, payCancelled);
        // payCancelled.setId(orderCancelled.getId());
        // payCancelled.setMenuId(orderCancelled.getMenuId());
        // payCancelled.setMenuName(orderCancelled.getMenuName());
        // payCancelled.setOrdererName(orderCancelled.getOrdererName());
        // payCancelled.setPrice(orderCancelled.getPrice());
        // payCancelled.setQuantity(orderCancelled.getQuantity());
        payCancelled.setStatus("payCancelled");
        payCancelled.publish();

        // req/res 패턴 처리 
        Delivery delivery = new Delivery();
        BeanUtils.copyProperties(payCancelled, delivery);
        // feignclient 호출
        PayApplication.applicationContext.getBean(DeliveryService.class).delivery(delivery);        
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public void setOrdererName(String ordererName) {
        this.ordererName = ordererName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }



}
