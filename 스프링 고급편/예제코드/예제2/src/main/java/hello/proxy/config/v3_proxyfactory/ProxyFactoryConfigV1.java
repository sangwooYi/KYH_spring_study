package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v1.*;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {
    @Bean
    public OrderControllerV1 orderControllerP(LogTrace logTrace) {
        ProxyFactory proxyFactory = new ProxyFactory(new OrderControllerV1Impl(orderServiceP(logTrace)));
        proxyFactory.addAdvisor(getAdvisor(logTrace));

        return (OrderControllerV1) proxyFactory.getProxy();
    }
    @Bean
    public OrderServiceV1 orderServiceP(LogTrace logTrace) {
        ProxyFactory proxyFactory = new ProxyFactory(new OrderServiceV1Impl(orderRepositoryP(logTrace)));
        proxyFactory.addAdvisor(getAdvisor(logTrace));

        return (OrderServiceV1) proxyFactory.getProxy();
    }
    @Bean
    public OrderRepositoryV1 orderRepositoryP(LogTrace logTrace) {

        ProxyFactory proxyFactory = new ProxyFactory(new OrderRepositoryV1Impl());
        proxyFactory.addAdvisor(getAdvisor(logTrace));

        return (OrderRepositoryV1) proxyFactory.getProxy();

    }

    @Bean
    public Advisor getAdvisor(LogTrace logTrace) {

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
    }

    @Bean
    public LogTrace logTraceP() {
        return new ThreadLocalLogTrace();
    }

}
