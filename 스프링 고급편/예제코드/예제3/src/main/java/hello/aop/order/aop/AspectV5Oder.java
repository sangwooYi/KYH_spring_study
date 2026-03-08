package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

/**
 *  어드바이저 순서 설정 Ver
 * @Order는 @Aspect 단위로만 설정 가능하다.
 * 따라서 어드바이저 순서를 지정하고싶으면 아래처럼
 * @Aspect 단위로 내부 클래스로 설정할 수 밖에 없음!
 *
 */
@Slf4j
public class AspectV5Oder {

    @Aspect
    @Order(2)
    public static class LogAspect {
        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature()); // joinPoint 시그니쳐
            return joinPoint.proceed();     // target 호출
        }
    }

    @Aspect
    @Order(1)
    public static class TransactionAspect {

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


}
