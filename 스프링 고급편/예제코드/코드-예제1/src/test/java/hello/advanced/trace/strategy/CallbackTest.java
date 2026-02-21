package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.template.Callback;
import hello.advanced.trace.strategy.template.CallbackLogic1;
import hello.advanced.trace.strategy.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CallbackTest {

    @Test
    void callbackTest1() {

        TimeLogTemplate template = new TimeLogTemplate();

        // 재사용 여부에 따라 어떤 방법으로 구현할지를 판단 할 줄알면 된다.

        // 당연히 미리 Callback 인터페이스를 implement 받아 구현한 클래스를 만들어 대입해도 된다!
        template.execute(new CallbackLogic1());

        // 익명 클래스 사용
        template.execute(new Callback() {
            @Override
            public void call() {
                log.info("비즈니스로직A 템플릿 콜백 패턴 & 익명 클래스 사용");
            }
        });

        // 람다 사용
        template.execute(() -> log.info("비즈니스로직1 실행, 템플릿콜백 패턴 사용"));
        template.execute(() -> log.info("비즈니스로직2 실행, 템플릿콜백 패턴 사용"));
    }
}
