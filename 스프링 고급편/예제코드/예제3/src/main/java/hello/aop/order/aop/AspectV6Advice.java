package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

/**

 * 어드바이스 종류에는
 * @Around 외에도
 * @Before / @AfterReturning / @AfterThrowing / @After 가 존재하지만
 * 사실 @Around 만 쓰면 된다. 이게 모든 기능을 다 갖고 있는 것
 * 다만 @Around 는 .proceed() 호출 안할경우, target이 호출이 안되어버리는 문제가 존재.
 * 따라서 간단한 로그같은 추가기능들을 안전하게 추가하고 싶다면
 * 나머지 어드바이스들을 선택하는것도 좋은 방안인 것 !
 */
@Slf4j
@Aspect
public class AspectV6Advice {

    @Around("hello.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // joinPoint 시그니쳐
        return joinPoint.proceed();     // target 호출
    }

    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();    // @Around 사용시 .proceed() 호출 안하면 target 호출이 안된다. 주의!

            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {

            // @AfterThrowing
            log.error("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw new IllegalStateException("doTransaction 예외 발생!");
        } finally {

            // @After
            log.info("[리소스 릴리스] {}", joinPoint.getSignature());
        }

    }

    // Around 제외한 나머지 어드바이스들은
    // JoinPoint 를 써야한다. ProceedingJoinPoint 를 쓰면 예외는 발생안하지만 그냥 실행이 안 된다.
    // ProceedingJoinPoint 는 @Around 에서만 사용!
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[doBefore] = {}", joinPoint.getSignature());
    }

    // returning 는 반환 Object의 param 명
    // 반환 타입이 실제 target 반환타입이어야 한다. ( 당연히 부모 타입으로 선언한것도 OK )
    // 타입 매칭이 적절하지 않으면 아예 호출이 안된다
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} / return = {}", joinPoint.getSignature(), result);
    }

    // throwing 은 Exception param 명
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} / message = {}", joinPoint.getSignature(), ex.getMessage());
    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
