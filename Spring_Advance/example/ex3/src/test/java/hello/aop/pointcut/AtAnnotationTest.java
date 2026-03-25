package hello.aop.pointcut;


import hello.aop.order.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * @annotation
 * -> 지정한 annotation 이 붙은 메서드들이 대상이 됨
 *
 * @args
 * -> 전달된 인수의 런타임 타입에 지정한 annotation 이 붙어있으면 대상이 됨
 * 메서드(@Check String testStr) 같은경우 @args(test.Check) 로 인해 해당 메서드가 대상이 됨!
 */
@Slf4j
// Aspect Config가 Import 되어야 제대로 AOP 적용이 된다.
@Import(AtAnnotationTest.AtAnnotationAspect.class)
@SpringBootTest
public class AtAnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService 프록시 = {}", memberService.getClass());
        memberService.hello("Hello Test");
    }


    /**
     * @annotation 지정한 경로의 어노테이션이 붙어있는 메서드들이 대상이 됨!
     */
    @Aspect
    static class AtAnnotationAspect {
        @Around("@annotation(hello.aop.order.aop.member.annotation.MethodAop)")
        public Object doAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@annotation] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
