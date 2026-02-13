package hello.advanced.app.v2;

import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import hello.advanced.trace.hellotrace.TraceId;
import hello.advanced.trace.hellotrace.TraceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(String itemId, TraceId traceId) {

        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, "OrderServiceV1.orderItem()");
            orderRepository.save(itemId, status.getTraceId());
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

}
