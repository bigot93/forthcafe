package forthcafe.external;

import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    // fallback message
    @Override
    public void pay(Pay pay) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!! Pay service is BUSY !!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!   Try again later   !!!!!!!!!!!!!!!!!!!!!");
    }

}
