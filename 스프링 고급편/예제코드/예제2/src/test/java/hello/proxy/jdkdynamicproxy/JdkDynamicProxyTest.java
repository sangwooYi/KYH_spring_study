package hello.proxy.jdkdynamicproxy;

import hello.proxy.jdkdynamicproxy.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {

        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        // JDK 동적 프록시 동적 생성법, 아래 방법을 기억하자!
        // 어떤 클래스로더를 쓸건지, 어떤 클래스를 기반으로 할건지, 핸들러가 뭔지 각각 인자로 전달해야 함
        // 참고로 target.getClass() == AImpl.class
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);

        proxy.call();

        log.info("클래스로더 = {}", AInterface.class.getClassLoader());
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        // JDK 동적 프록시 사용법, 아래 방법을 기억하자!
        // 어떤 클래스로더를 쓸건지, 어떤 클래스를 기반으로 할건지, 핸들러가 뭔지 각각 인자로 전달해야 함
        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);

        proxy.call();

        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());
    }
}
