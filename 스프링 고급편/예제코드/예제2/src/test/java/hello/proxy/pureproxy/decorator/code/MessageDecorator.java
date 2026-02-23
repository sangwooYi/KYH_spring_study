package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component{

    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }


    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        String result = component.operation();
        String decoMessage = String.format("*****%s*****", result);

        log.info("변경 전 = {}, Deco 적용 후 = {}", result, decoMessage);

        return decoMessage;
    }
}
