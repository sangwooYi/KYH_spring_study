package hello.proxy.jdkdynamicproxy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    /**
     * 일반적인 상황
     */
    @Test
    void reflectionV0() {
        Hello target = new Hello();

        // 공통 로직시작
        log.info("start");
        String result1 = target.callA();
        log.info("result = {}", result1);

        // 공통 로직시작
        log.info("start");
        String result2 = target.callB();
        log.info("result = {}", result2);
    }

    /**
     * 클래스, 메서드 메타정보를 통해
     * 동적으로 출력하는 방법 ( 오리지날 자바 문법 )
     * @throws Exception
     * 그냥 예제니까 Exception으로 퉁침, 실제로는 다 체크해줘야 함 ( 체크 예외들 잔뜩 필요 )
     */
    @Test
    void reflectionV1() throws Exception {
        // 클래스 정보 ( 클래스 경로를 통해 메타 데이터 얻을 수 있음,
        // 내부 클래스 접근은 $ 붙여 줘야함! 주의 )
        Class classHello = Class.forName("hello.proxy.jdkdynamicproxy.ReflectionTest$Hello");

        Hello target = new Hello();
        // 클래스의 메서드 정보 ( 메서드 명을 통해 가져올 수 있음 )
        Method methodA =  classHello.getMethod("callA");
        Object result1 = methodA.invoke(target);    // 리플렉션
        log.info("result1 = {}", result1);

        Method methodB =  classHello.getMethod("callB");
        Object result2 = methodB.invoke(target);  // 리플렉션
        log.info("result2 = {}", result2);
    }

    // 리플렉션을 통해 동적인 메서드 공통호출이 가능
    // but Class, Method 메타정보를 얻을 때, String  문자열을 통해 가져오므로,
    // 오타같은게 있어도, 런타임에서 체크된다 따라서 가능하면 쓰지 말 것!
    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("dynamicCall Start!");
        method.invoke(target);
    }

    static class Hello {
        public String callA() {
            log.info("callA 호출");
            return "A";
        }

        public String callB() {
            log.info("callB 호출");
            return "B";
        }
    }
}
