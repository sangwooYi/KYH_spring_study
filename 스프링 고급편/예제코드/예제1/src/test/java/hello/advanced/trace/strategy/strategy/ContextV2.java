package hello.advanced.trace.strategy.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 *  Strategy  를 Parameter 로 전달
 */
@Slf4j
public class ContextV2 {

    // 파라미터로 Strategy 를 전달
    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();    // 밀리초

        // 비즈니스 로직 호출 ( 변하는 부분 )
        strategy.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("resultTime = {}", resultTime);
    }
}
