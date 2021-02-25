
package forthcafe.external;

import org.springframework.cloud.openfeign.FeignClient; // import 되는지 확인 필요
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

// feignclient는 인터페이스 기술로 사용 가능
// url: 호출하고싶은 서비스 주소. http://localhost:8083 - application.yaml에 정의
@FeignClient(name="Delivery", url="${api.url.delivery}") 
public interface DeliveryService {

    // command
    @RequestMapping(method = RequestMethod.POST, path = "/deliveries", consumes = "application/json")
    // delivery 서비스의 deliveryCotroller를 호출
    public void delivery(@RequestBody Delivery delivery);

}