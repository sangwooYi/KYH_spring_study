package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeAdvice 실행 By 스프링 동적 프록시");
        long startTime = System.currentTimeMillis();

        // invocation 에서 알아서 다 해준다.
        Object result = invocation.proceed();
        // 여기서 명시적으로 target 의존성 주입할 필요 X

        long endTime = System.currentTimeMillis();
        long resultTime = endTime-startTime;
        log.info("TimeAdvice 종료 resultTime = {}ms", resultTime);

        return  result;
    }
}
