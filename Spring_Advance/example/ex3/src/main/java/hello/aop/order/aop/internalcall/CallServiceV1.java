package hello.aop.order.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *  내부호출 문제 대안1
 *  자기 자신 주입
 *
 *  해당 방안은 그냥 돌리면 순환참조 발생하므로
 *  spring.main.allow-circular-references=true
 *  조건을 application.properties 에 추가해야한다. (당연히 비권장임)
 *  + 생성자 주입이 아닌 수정자 주입을 해야한다.
 *
 */
@Slf4j
@Component
public class CallServiceV1 {

//    private CallServiceV1 callServiceV1;
//
//    @Autowired
//    public void setCallServiceV1(CallServiceV1 callServiceV1) {
//        this.callServiceV1 = callServiceV1;
//    }

    public void external() {
        log.info("external 호출");
//        callServiceV1.internal();
    }

    public void internal() {
        log.info("internal 호출");
    }
}
