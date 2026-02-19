package hello.advanced.trace.strategy.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 *  필드에 Strategy 를 보관하는 방식\
 *  ( 의존성 주입 방식 )
 */
@Slf4j
public class ContextV1 {

    private final Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();    // 밀리초

        // 비즈니스 로직 호출 ( 변하는 부분 )
        strategy.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("resultTime = {}", resultTime);
    }
}
