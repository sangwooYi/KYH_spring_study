package hello.proxy.app.v2;

import hello.proxy.app.v1.OrderRepositoryV1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderRepositoryV2 {

    public void save(String itemId) {
        // 저장 로직
        if (itemId.equals("ex")) {
            throw new IllegalStateException("OrderRepositoryV2.save() 예외 발생!");
        }
        sleep(1000);
    }

    private void sleep(int milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            log.debug("InterruptedException", e);
        }
    }
}


