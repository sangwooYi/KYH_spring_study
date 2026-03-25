package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldService {

    public String nameStore;

    public String logic(String name) {
        log.info("저장 name = {}", name);
        nameStore = name;

        sleep(1000);

        log.info("조회 nameStore = {}", nameStore);
        return nameStore;
    }

    private void sleep(int milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            log.debug("InterruptedException", e);
        }
    }

}
