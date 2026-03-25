package hello.proxy.config.v2_dyamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dyamicproxy.handler.LogTraceBasicHandler;
import hello.proxy.config.v2_dyamicproxy.handler.LogTraceFilterHandler;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    // 로그 남기는 패턴 설정
    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();
        InvocationHandler handler = new LogTraceFilterHandler(orderRepository, logTrace, PATTERNS);

        return (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(), new Class[]{OrderRepositoryV1.class}, handler);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1 orderService = new OrderServiceV1Impl(orderRepository(logTrace));
        InvocationHandler handler = new LogTraceFilterHandler(orderService, logTrace, PATTERNS);

        return (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(), new Class[]{OrderServiceV1.class}, handler);
    }

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1 orderController = new OrderControllerV1Impl(orderService(logTrace));
        InvocationHandler handler = new LogTraceFilterHandler(orderController, logTrace, PATTERNS);

        return (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(), new Class[]{OrderControllerV1.class}, handler);
    }

    @Bean
    public LogTrace logTraceDynamicVer() {
        return new ThreadLocalLogTrace();
    }
}
