package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV5 {

    private final TraceTemplate template;

    public void save(String itemId) {

        template.execute("OrderRepositoryV5.save() 실행!", () -> {
           if (itemId.equals("ex")) {
               throw new IllegalStateException("예외 발생!");
           }
           sleep(1000);
           return null;
        });

    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {  // InterruptedException 는 체크예외!
            log.debug("sleep error", e);
        }
    }
}
