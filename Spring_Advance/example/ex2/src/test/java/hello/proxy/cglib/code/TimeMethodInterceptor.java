package hello.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("TimeMethodInterceptor intercept 실행 By JDK 동적 프록시");
        long startTime = System.currentTimeMillis();

        // Method method 를 직접 쓰는거보다
        // MethodProxy proxy 를 활용해 invoke 를 호출하는게 더 빠르다고 함 (최적화 해 준 듯 )
        Object result = proxy.invoke(target, args);
        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("TimeMethodInterceptor 종료 resultTime = {}ms", resultTime);
        return result;
    }

}
