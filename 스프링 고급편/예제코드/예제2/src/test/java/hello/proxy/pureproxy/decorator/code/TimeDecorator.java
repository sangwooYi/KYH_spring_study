package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component{

    private Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long startDatetime = System.currentTimeMillis();

        String result = component.operation();

        long endDatetime = System.currentTimeMillis();
        long resultTime = endDatetime-startDatetime;
        log.info("소요시간 (밀리초) = {}", resultTime);

        return result;
    }
}
