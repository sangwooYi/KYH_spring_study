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
     * 익명 내부클래스 사용하여 구현
     */
    @Test
    void strategyV2() {

        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("Logic1 실행 By 익명클래스");
            }
        };
        Strategy strategyLogic2 = new Strategy() {
            @Override
            public void call() {
                log.info("Logic2 실행 By 익명클래스");
            }
        };

        ContextV1 context1 = new ContextV1(strategyLogic1);
        ContextV1 context2 = new ContextV1(strategyLogic2);

        context1.execute();
        context2.execute();

    }

    // 인터페이스의 메서드가 한 개만 있는경우는 람다로 구현 가능!
    // 이 예제에 경우 call 메서드 한개만 존재하므로 자동으로 람다의 로직을 -> call 메서드에 대응시켜 줌
    // 참고로 인터페이스에 @FunctionalInterface 를 걸 수 있는데 이 역할은
    // -> 메서드 2개 이상 기재해두면 컴파일 에러를 일으킨다. 
    // 메서드가 한개 분이면 따라서 저 어노테이션과 관계없이 람다로 알아서 처리해 준다. FunctionalInterface 는 그냥 체크용!
    @Test
    void strategyV3() {
        ContextV1 context1 = new ContextV1(() -> log.info("Logic1 실행 By 람다"));
        ContextV1 context2 = new ContextV1(() -> log.info("Logic2 실행 By 람다"));

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

