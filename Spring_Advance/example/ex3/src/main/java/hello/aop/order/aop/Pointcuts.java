package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 *  포인트컷 일괄 관리 + 참조
 */
public class Pointcuts {

    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {}  // pointcut signature

    // 클래스 이름 패턴 *Service 인것 (Service로 끝나는 메서드) ( 패키지 경로는 상관 X )
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
