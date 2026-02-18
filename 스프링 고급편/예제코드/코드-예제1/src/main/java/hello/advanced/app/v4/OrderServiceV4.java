package hello.advanced.app.v4;

import hello.advanced.trace.hellotrace.TraceId;
import hello.advanced.trace.hellotrace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepository;
    private final ThreadLocalLogTrace trace;

    public void orderItem(String itemId, TraceId traceId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderServiceV4.orderItem()");
            orderRepository.save(itemId, status.getTraceId());
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

}
