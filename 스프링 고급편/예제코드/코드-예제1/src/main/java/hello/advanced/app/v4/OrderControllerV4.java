package hello.advanced.app.v4;

import hello.advanced.trace.hellotrace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;
    private final ThreadLocalLogTrace trace;

    @GetMapping("/v4/request")
    public String request(@RequestParam String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV4.request()");
            orderService.orderItem(itemId, status.getTraceId());
            trace.end(status);
            return "OK";
        } catch (Exception e) {
            trace.exception(status, e);
            return e.getMessage();
        }
    }

}
