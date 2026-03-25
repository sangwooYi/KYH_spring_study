package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecoratorV2 extends AbstractDecorator {

    public TimeDecoratorV2(Component component) {
        super(component);
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();
        String result = super.operation();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("소요시간 = {}ms", resultTime);

        return result;
    }
}
