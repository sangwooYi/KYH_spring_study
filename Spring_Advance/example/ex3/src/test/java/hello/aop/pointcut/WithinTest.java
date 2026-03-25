package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

/**
 *  within
 *  타입이 매칭되면 그 안의 모든 메서드를 매칭하는 것
 *  execution 에서 타입부분만 사용하는 개념이라 이해하면 좋음
 *  
 *  즉 해당 타입에 속하면 (within) 적용하겠다는 것
 *  주의할 건 within 은 정확히 같은 타입이어야만 한다 ( 부모 클래스 일지라도 여기선 False,
 *  execution 과 차이점 중 하나! )
 */
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void withinExact() {
        pointcut.setExpression("within(hello.aop.order.aop.member.MemberServiceImpl)");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 패턴적용이나, 생략도 비슷한 맥락이다. ( execution 과 달리 Parameter 관련 지정은 없음! )
    @Test
    void withinStar() {
        pointcut.setExpression("within(hello.aop.order.aop.member.*Service*)");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(hello.aop..*)");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
