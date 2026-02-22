package hello.proxy.app.v3;

import hello.proxy.app.v1.OrderRepositoryV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderRepositoryV3 {

    public void save(String itemId) {
        // 저장 로직
        if (itemId.equals("ex")) {
            throw new IllegalStateException("OrderRepositoryV3.save() 예외 발생!");
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


