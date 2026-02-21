package hello.advanced.trace.strategy.template;

import hello.advanced.trace.strategy.strategy.Strategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeLogTemplate {

    public void execute(Callback callback) {
        long startTime = System.currentTimeMillis();    // 밀리초

        // 비즈니스 로직 호출 ( 변하는 부분 )
        callback.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("resultTime = {}", resultTime);
    }

}
