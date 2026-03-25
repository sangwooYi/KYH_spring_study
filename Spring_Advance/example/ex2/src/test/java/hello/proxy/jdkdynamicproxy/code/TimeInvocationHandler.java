package hello.proxy.jdkdynamicproxy.code;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK 동적 프록시 사용법
 * InvocationHandler 를 implement 하여 구현
 */
@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target;

    public TimeInvocationHandler(Object object) {
        this.target = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.info("Time Proxy 실행 By JDK 동적 프록시");
        long startTime = System.currentTimeMillis();
        Object result = method.invoke(target, args);
        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("Time proxy 종료 resultTime = {}ms", resultTime);

        return  result;
    }
}
