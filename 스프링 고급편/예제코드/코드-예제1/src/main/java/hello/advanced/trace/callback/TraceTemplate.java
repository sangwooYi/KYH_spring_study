package hello.advanced.trace.callback;

import hello.advanced.trace.hellotrace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    // 이렇게 클래스와 별도로 독립적으로 제네릭 타입을 지정하여 선언할 수 있다!
    // 이게 제네릭 메서드 ( 당연히, 제네릭클래스를 선언하는 경우라면
    // 그 클래스 내부의 메서드들은 제네릭 메서드 형태를 부여할 필요는 없다! )
    public <T>  T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            // 로직 호출 ( 이부분만 자식이 구현하도록 템플릿 세팅 )
            T result = callback.call();
            trace.end(status);
            return result;

        } catch (Exception e) {

            trace.exception(status, e);
            throw e;

        }
    }
}
