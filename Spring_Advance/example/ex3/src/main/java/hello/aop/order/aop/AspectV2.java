package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 *  포인트컷 분리 Ver
 *
 *  장점.
 *  1. 포인트컷 재활용 가능, 필요시 포인트컷 일괄 관리도 가능
 *  2. IntelliJ 사용시 @Pointcut 은 자동 완성지원까지 해 줌! @Around 는 안 됨
 *
 */
@Slf4j
@Aspect
public class AspectV2 {

    // String 을 넣는것이므로 아래처럼 상수로 빼서 대입도 가능하다!
    // ( 근데 이렇게 하면 컴파일 단계에서 에러 확인히 힘들어 비권장!
//    private final String TEST = "execution(* hello.aop.order..*(..))";
//    @Pointcut(TEST)
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {}  // pointcut signature



    // 이렇게 @Pointcut 으로 포인트컷을 별도로 분리 가능!
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // joinPoint 시그니쳐
        return joinPoint.proceed();     // target 호출
    }

}
