package hello.proxy.config.v2_dyamicproxy.handler;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 메서드 필터링 기능 추가 Ver 핸들러
 */
public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;

    public LogTraceFilterHandler(Object object, LogTrace logTrace, String[] patterns) {
        this.target = object;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();

        // 매칭이 안되면 그냥 PASS ( 반환은 반드시 아래처럼 해주어야 프록시가 타겟을 호출해 줌 )
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return method.invoke(target, args);
        }

        TraceStatus status = null;
        String className = method.getDeclaringClass().getSimpleName();
        try {
            status = logTrace.begin(className + "." + method.getName() + " 호출");
            Object result = method.invoke(target, args);
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }

    }
}
