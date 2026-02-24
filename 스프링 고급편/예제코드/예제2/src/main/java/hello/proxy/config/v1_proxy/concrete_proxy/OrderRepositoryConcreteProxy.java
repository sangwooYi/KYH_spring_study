package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {

    private OrderRepositoryV2 repository;
    private LogTrace logTrace;

    public OrderRepositoryConcreteProxy(OrderRepositoryV2 repository, LogTrace logTrace) {
        this.repository = repository;
        this.logTrace = logTrace;
    }

    @Override
    public void save(String itemId) {

        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderServiceConcreteProxy 실행 by Concrete 프록시");
            repository.save(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
