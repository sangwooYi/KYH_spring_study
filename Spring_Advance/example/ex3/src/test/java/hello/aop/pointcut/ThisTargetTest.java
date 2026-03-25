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

@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
// true 가 디폴트 ( 항상 CGLIB 프록시로 생성 , 당연히 구체클래스가 아예 없으면 JDK로 생성) 
// -> true 이면 무조건 CGLIB 프록시로 생성 ( target 기반 )
// -> false 로 할 경우 인터페이스 기반이면 JDK 프록시로 생성 됨
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy = {}", memberService.getClass());
        memberService.hello("TEST !!");
    }

    @Aspect
    static class ThisTargetAspect {
        @Around("this(hello.aop.order.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
        @Around("target(hello.aop.order.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // JDK 프록시로 생성 될 경우 얘는 적용 안된다!
        @Around("this(hello.aop.order.aop.member.MemberServiceImpl)")
        public Object doThisConcreteClass(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-concrete] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
        @Around("target(hello.aop.order.aop.member.MemberServiceImpl)")
        public Object doTargetConcreteClass(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-concrete] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

}
