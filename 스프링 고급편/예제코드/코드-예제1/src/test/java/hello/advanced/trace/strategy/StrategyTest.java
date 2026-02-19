package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.strategy.ContextV1;
import hello.advanced.trace.strategy.strategy.Strategy;
import hello.advanced.trace.strategy.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.naming.Context;

/**
 * 전략 패턴 기본예제
 */
@Slf4j
public class StrategyTest {

    @Test
    void strategyV0() {
        this.logic1();
        this.logic2();
    }

    @Test
    void strategyV1() {

        ContextV1 context1 = new ContextV1(new StrategyLogic1());
        ContextV1 context2 = new ContextV1(new StrategyLogic2());

        context1.execute();
        context2.execute();
    }


    /**
     *   핵심 비즈니스 로직에
     *   시간 측정 부가기능이 섞여있는 상태
     */
    private void logic1() {
        long startTime = System.currentTimeMillis();    // 밀리초

        // 비즈니스 로직 ( 있다고 가정 )
        log.info("비즈니스 로직1 실행");

        long endTime = System.currentTimeMillis();

        long resultTime = endTime-startTime;
        log.info("resultTime = {}", resultTime);

    }

    private void logic2() {
        long startTime = System.currentTimeMillis();    // 밀리초

        // 비즈니스 로직 ( 있다고 가정 )
        log.info("비즈니스 로직2 실행");

        long endTime = System.currentTimeMillis();

        long resultTime = endTime-startTime;
        log.info("resultTime = {}", resultTime);

    }
}

