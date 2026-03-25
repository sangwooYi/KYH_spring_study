package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 전략 패턴 기본예제
 */
@Slf4j
public class ContextV2Test {

    /**
     *  Parameter 전달 방식 Strategy 패턴의 장점
     *  Context 는 하나만 생성해도 된다 !!
     *  execute 로직을 동적으로 할당하면 됨
     *
     *  이렇게 Strategy ( Callback ) 를 콜백 형태로 Context ( Template ) 에 Parameter로 전달하는 형태를
     *  "템플릿 콜백 패턴"이라고 칭함 ( GOF 에서 나온 패턴은 아니고
     *  스프링 쪽에서만 자체적으로 부르는 별칭같은 거임. 결국 이건 전략패턴! )
     *
     */
    @Test
    void contextV1() {
        ContextV2 context = new ContextV2();

        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
        log.info("=======================");
        context.execute(() -> log.info("로직1 실행 Parameter로 전달 Ver"));
        context.execute(() -> log.info("로직2 실행 Parameter로 전달 Ver"));
    }
}

