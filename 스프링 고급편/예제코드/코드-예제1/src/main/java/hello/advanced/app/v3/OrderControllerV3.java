package hello.advanced.app.v3;

import hello.advanced.trace.hellotrace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String request(@RequestParam String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV3.request()");
            orderService.orderItem(itemId, status.getTraceId());
            trace.end(status);
            return "OK";
        } catch (Exception e) {
            trace.exception(status, e);
            return e.getMessage();
        }
    }

}
