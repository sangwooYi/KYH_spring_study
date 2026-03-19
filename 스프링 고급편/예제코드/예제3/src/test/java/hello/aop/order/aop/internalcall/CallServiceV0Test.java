package hello.aop.order.aop.internalcall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceV0;

    /**
     *  내부 호출 Test
     */
    @Test
    void doTest() {
        callServiceV0.external();      // internal 은 AOP 적용이 안됨!
        
        callServiceV0.internal();   // 얜 당연히 AOP 적용 됨 ( 외부 호출 )
    }
    

}