package hello.aop.order.aop.exam.aop;

import hello.aop.order.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 프록시 통해서 에러 발생시 자동으로 재실행 요청하는 프로그램 구현 가능!
 * ( 유용하게 사용 된다! )
 */
@Slf4j
@Aspect
public class RetryAspect {

    // 단순 로그출력용이므로 안전하게 Before 사용
    // annotation 은 해당 어노테이션이 붙은 메서드들을 자동으로 프록시 대상 설정함.
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable{
        // 어드바이스 로직은 여기서 작성하는 것!
        log.info("[retry] {} , retry = {}", joinPoint.getSignature(), retry);
        int maxRetry = retry.value();
        Exception exceptionHolder = null;

        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry action] try count = {} / {}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }

}
