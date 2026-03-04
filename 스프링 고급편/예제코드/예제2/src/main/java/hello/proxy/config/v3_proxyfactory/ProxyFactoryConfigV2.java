package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v1.*;
import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {
    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        ProxyFactory proxyFactory = new ProxyFactory(new OrderControllerV2(orderServiceV2(logTrace)));
        proxyFactory.addAdvisor(getAdvisor(logTrace));

        return (OrderControllerV2) proxyFactory.getProxy();
    }
    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        ProxyFactory proxyFactory = new ProxyFactory(new OrderServiceV2(orderRepositoryV2(logTrace)));
        proxyFactory.addAdvisor(getAdvisor(logTrace));

        return (OrderServiceV2) proxyFactory.getProxy();
    }
    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {

        ProxyFactory proxyFactory = new ProxyFactory(new OrderRepositoryV2());
        proxyFactory.addAdvisor(getAdvisor(logTrace));

        return (OrderRepositoryV2) proxyFactory.getProxy();

    }

    @Bean
    public Advisor getAdvisor(LogTrace logTrace) {

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
    }

    @Bean
    public LogTrace logTraceV22() {
        return new ThreadLocalLogTrace();
    }

}
