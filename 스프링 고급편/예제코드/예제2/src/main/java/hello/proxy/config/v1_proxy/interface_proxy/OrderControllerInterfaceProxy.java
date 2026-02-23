package hello.proxy.config.v1_proxy.interface_proxy;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1 {

    private final OrderControllerV1 target;
    private final LogTrace logTrace;

    @Override
    @GetMapping("/proxy/request")
    public String request(String itemId) {
        TraceStatus status = null;
        try {

            status = logTrace.begin("OrderController.request 실행 By Proxy");
            // target 호출
            String result = target.request(itemId);
            logTrace.end(status);
            return result;

        } catch (Exception e) {

            logTrace.exception(status, e);
            log.debug("예외 발생", e);
            return e.getMessage();
        }
    }

    @Override
    @GetMapping("/proxy/no-log")
    public String noLog() {
        String result = target.noLog();
        return result;
    }
}
