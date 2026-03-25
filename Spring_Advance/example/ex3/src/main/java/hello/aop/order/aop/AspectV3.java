package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 *  어드바이스 추가 Ver
 */
@Slf4j
@Aspect
public class AspectV3 {

    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {}  // pointcut signature

    // 클래스 이름 패턴 *Service 인것 (Service로 끝나는 메서드) ( 패키지 경로는 상관 X )
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}


    // 이렇게 @Pointcut 으로 포인트컷을 별도로 분리 가능!
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // joinPoint 시그니쳐
        return joinPoint.proceed();     // target 호출
    }

    // hello.aop.order 패키지 경로에 속하면서 && Service 로 끝나는 메서드
    // && and, || or, ! not 논리연산자 적용 가능
    @Around("allOrder() && allService()")
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
