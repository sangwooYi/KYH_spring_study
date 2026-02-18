package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {

    public ThreadLocal<String> nameStore = new ThreadLocal<>();

    public String logic(String name) {
        log.info("저장 name = {}", name);
        nameStore.set(name);

        sleep(1000);

        log.info("조회 nameStore = {}, name = {}", nameStore, nameStore.get());
        return nameStore.get();
    }

    private void sleep(int milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            log.debug("InterruptedException", e);
        }
    }

}
