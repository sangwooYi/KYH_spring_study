package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberService;
import hello.aop.order.aop.member.annotation.ClassAop;
import hello.aop.order.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy = {}", memberService.getClass());
        memberService.hello("FOR TEST");
    }

    @Aspect
    static class ParameterAspect {
    
        // member 패키지 하위에 모든 파일들의 모든 메서드가 대상
        @Pointcut("execution(* hello.aop.order.aop.member..*.*(..))")
        private void allMember(){}

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object[] args = joinPoint.getArgs();
            Object arg1 = joinPoint.getArgs()[0];  // 첫번째 args 가 나온다.
            log.info("[logArgs1] args = {} / arg1 = {}", args, arg1);

            return joinPoint.proceed();
        }

        // args 사용하여 param 가져오기 아래처럼 하면 첫번째 param 을 arg 란 이름으로 가져 옴
        @Around("allMember() && args(arg, ..)")     // 참고로 이 경우는 String 이므로 String args 로 선언해도 된다!
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {

            log.info("[logArgs2] = {} / arg1 = {}", joinPoint.getSignature(), arg);

            return joinPoint.proceed();
        }

        // 단순 출력은 @Before 사용하는게 안전하긴 함!!
        @Before("allMember()  && args(arg, ..)")
        public void logArgs3(String arg) {  // 물론 Object arg 로도 선언 가능!
            log.info("[logArgs3] arg = {}", arg);
        }

        // this vs target 
        // this 는 스프링 컨테이너에 등록된 빈을 가르킴 ( 여기선 CGLIB 프록시가 obj 에 담김)
        // argNames 는 내가 선언하는 parameter 의 변수명을 정의
        // 얘네들은 정확히 대상을 exact 하게 지정해줘야하며 패턴 사용 불가, 둘다 부모타입도 허용
        @Before(value = "allMember() && this(obj)", argNames = "joinPoint, obj")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this] {} , obj = {} / {}", joinPoint.getSignature(), obj.getClass(), obj);
        }

        // 말그대로 target (실제 대상)을 가르킴 ( 여기선 MemberService )
        @Before(value = "allMember() && target(obj)", argNames = "joinPoint, obj")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target] {} , obj = {}", joinPoint.getSignature(), obj.getClass());
        }

        @Before(value = "allMember() && @target(annotation)", argNames = "joinPoint, annotation")
        public void atTargetArgs(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target] {} , annotation = {}", joinPoint.getSignature(), annotation);
        }

        @Before(value = "allMember() && @within(annotation)", argNames = "joinPoint, annotation")
        public void atWithinArgs(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within] {} , annotation = {}", joinPoint.getSignature(), annotation);
        }

        // @annotation 은 어노테이션에 전달된 value 를 꺼내올 수 있다.
        @Before(value = "allMember() && @annotation(annotation)", argNames = "joinPoint, annotation")
        public void atAnnotationArgs(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation] {} , annotation Value = {}", joinPoint.getSignature(), annotation.value());
        }


    }

}
