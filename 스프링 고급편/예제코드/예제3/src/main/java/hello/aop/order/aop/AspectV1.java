package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
// 어드바이저로 설정하려면 @Aspect 를 넣어주어야 함 ( 아니면 수동으로 구현해야 함 )
@Aspect
public class AspectV1 {

    // 이게 포인트컷 , 패키지는 java 이하 경로부터 적어줘야 함
    // hello.aop.order 패키지 경로 이하에 모든 메서드에 대해 적용, parameter는 상관 X (..) 의 의미
    @Around("execution(* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // joinPoint 시그니쳐
        return joinPoint.proceed();     // target 호출
    }

}
