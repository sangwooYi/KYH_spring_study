package hello.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();    // 밀리초

        // 비즈니스 로직
        this.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("resultTime = {}", resultTime);

    }

    // 변하는 부분 (주로 핵심 로직) 을 호출해서 사용 ( 이부분은 실제 로직에 맞게 구현 )
    protected abstract void call();
}
