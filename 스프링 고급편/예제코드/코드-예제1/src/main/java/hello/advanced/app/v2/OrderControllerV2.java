package hello.advanced.app.v2;

import hello.advanced.trace.hellotrace.HelloTraceV2;
import hello.advanced.trace.hellotrace.TraceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(@RequestParam String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV1.request()");
            orderService.orderItem(itemId, status.getTraceId());
            trace.end(status);
            return "OK";
        } catch (Exception e) {
            trace.exception(status, e);
            return e.getMessage();
        }
    }

}
