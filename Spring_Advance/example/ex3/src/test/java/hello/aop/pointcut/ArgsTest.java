package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.assertThat;

import hello.aop.order.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;


/**
 *  args 는 execution 에 Parameter 부분에 해당한다고 이해하면 된다.
 *  args 는 부모 타입까지도 체크 해줌 !  ( 여기선 오히려 execution 이 안 됨! )
 */
public class ArgsTest {

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Test
    void args() {
        assertThat(pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue();

        assertThat(pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // 없어야만 함
        assertThat(pointcut("args()").matches(helloMethod, MemberServiceImpl.class)).isFalse();

        // 뭐든지 허용
        assertThat(pointcut("args(..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // 1개여야만 하고, 대신 타입은 전부 허용
        assertThat(pointcut("args(*)").matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // 첫번째 parameter는 String 이어야만 하며, 그 외에는 전부 하용
        assertThat(pointcut("args(String, ..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * execution(* *(java.io.Serializable)): 메서드의 시그니처로 판단 (정적)
     * args(java.io.Serializable): 런타임에 전달된 인수로 판단 (동적)
     */
    @Test
    void argsVsExecution() {

        assertThat(pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        // 여기선 parameter 가 부모타입으로 들어와도 체크 됨!
        assertThat(pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();

        assertThat(pointcut("execution(* *(String))").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        // execution 의 경우 param 은 부모타입 체크 안 됨
        assertThat(pointcut("execution(* *(Object))").matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
}
