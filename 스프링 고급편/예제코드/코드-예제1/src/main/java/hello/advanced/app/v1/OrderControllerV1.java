package hello.advanced.app.v1;

import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.TraceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String request(@RequestParam String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV1.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "OK";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

}
