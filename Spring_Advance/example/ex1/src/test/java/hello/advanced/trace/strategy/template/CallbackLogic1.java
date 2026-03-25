package hello.advanced.trace.strategy.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallbackLogic1 implements Callback {

    @Override
    public void call() {
        log.info("템플릿 콜백패턴 로직11~~ 미리 클래스 구현");
    }
}
