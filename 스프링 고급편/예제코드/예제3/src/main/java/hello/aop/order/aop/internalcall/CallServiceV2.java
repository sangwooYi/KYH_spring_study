package hello.aop.order.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 *  내부호출 문제 대안2
 *  지연 호출 (LAZY)
 *  ObjectProvider 를 사용하여 실제 호출시점에 빈을 호출하는 방식 구현 가능
 *  LAZY!!
 *  순환참조 발생 안하므로 V3 방식 (구조 분리)가 안되는 상황이라면
 *  LAZY 방식을 적용!
 *
 */
@Slf4j
@Component
public class CallServiceV2 {

//    private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    public void external() {
        log.info("external 호출");

        // 실제 호출하는 단계에서 지연 호출
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal();
    }

    public void internal() {
        log.info("internal 호출");
    }
}
