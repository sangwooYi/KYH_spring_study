package hello.proxy.advisor;

import hello.proxy.common.ServiceImpl;
import hello.proxy.common.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

@Slf4j
public class MultiAdvisorTest {

    @Test
    @DisplayName("여러 프록시")
    void multiAdvisor() {
        // client -> proxy2(advisor2) -> proxy1(advisor1) -> target 흐름
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy1 = (ServiceInterface) proxyFactory.getProxy();

        // 프록시 2 생성
        // 주의! target에 프록시를 넣어주어야 프록시 체이닝이 형성 됨!
        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        proxyFactory2.addAdvisor(advisor2);

        ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();
        
        // proxy2 -> proxy1 -> target의 find 호출
        proxy2.find();
    }

    @Test
    @DisplayName("하나의 프록시에 여러 어드바이저 적용")
    void multiAdvisorV2() {
        // client -> proxy -> advisor2 -> advisor1 -> target 흐름으로

        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        // 프록시 생성
        ProxyFactory proxyFactory = new ProxyFactory(new ServiceImpl());

        // 순서대로 호출되므로 넣는 순서 조심!
        // addAdvisors 로 , 형태로 한 번에 넣어도 되고,
        // addAdvisor 를 순서대로 호출해도 된다!
//        proxyFactory.addAdvisors(advisor2, advisor1);
        proxyFactory.addAdvisor(advisor2);
        proxyFactory.addAdvisor(advisor1);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.find();
    }

    // 스프링 Advice 는 MethodInterceptor 인터페이스 통해 구현함
    static class Advice1 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("Advice1 호출");
            return invocation.proceed();
        }
    }

    static class Advice2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("Advice2 호출");
            return invocation.proceed();
        }
    }

}
