package forthcafe;
import forthcafe.config.kafka.KafkaProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.openfeign.EnableFeignClients; // import 되는지 확인 필요


@SpringBootApplication
@EnableBinding(KafkaProcessor.class)
@EnableFeignClients
public class PayApplication {
    protected static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(PayApplication.class, args);
    }
}
