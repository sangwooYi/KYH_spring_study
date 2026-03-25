package hello.proxy.proxyfactory;

import hello.proxy.common.ConcreteService;
import hello.proxy.common.ServiceImpl;
import hello.proxy.common.ServiceInterface;
import hello.proxy.common.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

/**
 *  아래 2 Step 만 해주면 스프링 동적 프록시 설정 끝
 *  1. ProxyFactory 생성시 생성자에 target 넘겨줌
 *     ( target이 interface 기반이면 JDK 동적 프록시
 *                구현체만 있으면 CGLIB 프록시 호출 해 줌 )
 *                
 *  2. 내가 사용할 Advice 정보를 .addAdvice() 로 넘겨줌
 */
@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy() {

        ServiceInterface target = new ServiceImpl();

        // 이렇게 ProxyFactory 생성시 생성자에 target 정보를 넘겨주면
        // 나머지를 알아서 다 해준다!
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 내가 구현한 Advice 정보 넘겨주면 된다.
        proxyFactory.addAdvice(new TimeAdvice());
        // setProxyTargetClass 를 true 로 설정하면 무조건 CGLIB 로 생성 됨
//        proxyFactory.setProxyTargetClass(true);

        // 이렇게 .getProxy() 호출하면 알아서 프록시를 만들어서 반환해 준다.
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        this.printProxyTargetInfo(target, proxy, ServiceInterface.class);

        proxy.find();
        proxy.save();
    }

    @Test
    @DisplayName("구체클래스인 경우는 CGLIB 프록시 사용")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        // 구체클래스인 경우는 이 값이 true 이건 false 이건 무시한다 ( 어차피 무조건 CGLIB 이니까 ) 
        // 인터페이스가 있는 클래스가 target인 경우만 해당 메서드가 유효함
        // proxyFactory.setProxyTargetClass(false);
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        this.printProxyTargetInfo(target, proxy, ConcreteService.class);

        proxy.call();
    }

    // 변수로 클래스 메타정보를 받으려면 Class<?> 타입으로 선언해 주어야 함! 기억하자.
    private void printProxyTargetInfo(Object target, Object proxy, Class<?> targetClass) {

        if (targetClass.isInstance(proxy) && targetClass.isInstance(target)) {

            log.info("targetClass = {}", target.getClass());
            log.info("proxyClass = {}", proxy.getClass());

            // JDK 동적 프록시 여부
            log.info("AopUtils.isJdkDynamicProxy(proxy) = {}", AopUtils.isJdkDynamicProxy(proxy));
            // CGLIB 프록시 여부
            log.info("AopUtils.isCglibProxy(proxy) = {}", AopUtils.isCglibProxy(proxy));
            // 이건 스프링 동적 프록시 사용했으면 항상 참
            log.info("AopUtils.isAopProxy(proxy) = {}", AopUtils.isAopProxy(proxy));
        }

    }
}
