package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 *  포인트컷 참조 Ver
 *
 *  IntelliJ 사용시, 포인트컷 위치 참조 자동완성 된다!!
 */
@Slf4j
@Aspect
public class AspectV4 {

    @Around("hello.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // joinPoint 시그니쳐
        return joinPoint.proceed();     // target 호출
    }

    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.error("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw new IllegalStateException("doTransaction 예외 발생!");
        } finally {
            log.info("[리소스 릴리스] {}", joinPoint.getSignature());
        }

    }

}
