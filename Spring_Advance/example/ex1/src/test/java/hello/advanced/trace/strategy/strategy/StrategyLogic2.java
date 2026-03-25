package hello.advanced.trace.strategy.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrategyLogic2 implements Strategy{


    @Override
    public void call() {
        log.info("비즈니스 로직 2 실행 전략패턴 활용");
    }
}
