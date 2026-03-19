package hello.aop.order.aop.internalcall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV2Test {

    @Autowired
    CallServiceV2 callServiceV2;

    /**
     *  내부 호출 Test
     *  얘는 지연호출이므로 순환참조 X
     */
    @Test
    void doTest() {
        callServiceV2.external();
    }
    

}