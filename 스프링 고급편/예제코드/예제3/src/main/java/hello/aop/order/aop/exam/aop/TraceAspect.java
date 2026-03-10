package hello.aop.order.aop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class TraceAspect {

    // 단순 로그출력용이므로 안전하게 Before 사용
    // annotation 은 해당 어노테이션이 붙은 메서드들을 자동으로 프록시 대상 설정함.
    @Before("@annotation(hello.aop.order.aop.exam.annotation.Trace)")
    public void doTrace(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info("[trace] {} , args = {}", joinPoint.getSignature(), args);
    }

}
