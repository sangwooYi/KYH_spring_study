package hello.aop.pointcut;

import hello.aop.order.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 *  주의
 *  args, @args, @target 은 절대 독립적으로 사용하면 안 됨. ( 동적으로 체크하는 포인트컷들 )
 *  execution, within 등 기본 포인트컷과 함께 사용하여 디테일한 대상 제한 목적으로만 쓸 것!
 *  -> 안그러면 final 같은 빈들도 대상으로 설정되어버려 아예 실행 때 에러발생 되어버린다.
 */
@Slf4j
@Import({AtTargetAtWithinTest.Config.class})
@SpringBootTest
public class AtTargetAtWithinTest {

    @Autowired
    Child child;

    @Test
    void success() {
        log.info("child Proxy = {}", child.getClass());
        child.childMethod();
        child.parentMethod();
    }

    static class Config {
        @Bean
        public Parent parent() {
            return new Parent();
        }
        @Bean
        public Child child() {
            return new Child();
        }
        @Bean
        public AtTargetAtWithinAspect atTargetAtWithinAspect() {
            return new AtTargetAtWithinAspect();
        }
    }

    static class Parent {
        public void parentMethod() {}
    }

    @ClassAop
    static class Child extends Parent {
        public void childMethod(){}

        // 당연히 이렇게 오버라이딩 하면 within 에서도 대상이 됨
//        @Override
//        public void parentMethod() {
//            log.info("hehe");
//        }
    }

    @Aspect
    static class AtTargetAtWithinAspect {
        // hello.aop 패키지 하위의 모든 파일에 대한 모든 메서드, param 은 상관 X
        // And ClassAop 적용 어노테이션 클래스 대상으로 ( @target 은 부모 메서드도 포함 따라서 parentMethod() 도 호출 )
        @Around("execution(* hello.aop..*(..)) && @target(hello.aop.order.aop.member.annotation.ClassAop)")
        public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@target] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // within 은 부모타입에만 있는 메서드는 대상 X  따라서 parentMethod() 는 호출 X
        // 타겟 클래스가 직접 갖고 있는 메서드만 대상이 된다!!
        @Around("execution(* hello.aop..*(..)) && @within(hello.aop.order.aop.member.annotation.ClassAop)")
        public Object atWithin(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@within] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

}
