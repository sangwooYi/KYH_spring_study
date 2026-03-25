package hello.proxy.pureproxy.decorator.code;


/**
 * 의존성 주입부분이 항상 중복되므로
 * 추상 클래스 통해 데코레이터를 다루는 ver
 */
public abstract class AbstractDecorator implements Component{

    // 상속받은 구현체들도 참조해야하므로 protected 접근제어자로
    protected Component component;

    public AbstractDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        return component.operation();
    }
}
