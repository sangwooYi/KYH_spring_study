package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

    public void save(String itemId) {

        // 저장로직
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        this.sleep(1000);
    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {  // InterruptedException 는 체크예외!
            log.debug("sleep erro", e);
        }
    }
}
