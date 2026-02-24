package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderControllerConcreteProxy extends OrderControllerV2 {

    private OrderControllerV2 orderController;
    private LogTrace logTrace;

    // 부모한테 넘겨줄 생성자 인자가 필요하다.
    public OrderControllerConcreteProxy(OrderControllerV2 orderController, LogTrace logTrace) {
        // 부모 클래스를 직접 사용할 일이 없으므로 null 을 넘겨주어도 된다
        // or 부모 클래스에 기본 생성자를 별도로 선언!  (둘 중에 하나를 선택하면 됨)
        super(null);
        this.orderController = orderController;
        this.logTrace = logTrace;
    }

    @Override
    public String noLog() {
        log.info("noLog 호출 By Concrete 프록시");
        return orderController.noLog();
    }

    @Override
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderController Concrete 프록시 호출");
            String result = orderController.request(itemId);
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
