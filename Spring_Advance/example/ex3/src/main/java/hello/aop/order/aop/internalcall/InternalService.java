package hello.aop.order.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InternalService {
    
    public void internalService() {
        log.info("internalService 구조 분리 후 호출");
    }
}
