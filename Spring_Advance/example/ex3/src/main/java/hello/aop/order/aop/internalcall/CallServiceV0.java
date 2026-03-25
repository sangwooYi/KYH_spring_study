package hello.aop.order.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    public void external() {
        log.info("external 호출");
        this.internal();
    }

    public void internal() {
        log.info("internal 호출");
    }
}
