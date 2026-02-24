package hello.advanced.app.v1;

import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.TraceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {

    private final HelloTraceV1 trace;

    public void save(String itemId) {

        TraceStatus status = null;

        try {
            status = trace.begin("OrderRepositoryV1.save()");
            // 저장로직
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            this.sleep(1000);
            trace.end(status);
        } catch (IllegalStateException e) {
            trace.exception(status, e);
            throw e;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }

    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {  // InterruptedException 는 체크예외!
            log.debug("sleep erro", e);
        }
    }
}
