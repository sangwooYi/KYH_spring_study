package hello.advanced.trace.template;


import hello.advanced.trace.hellotrace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message) {
        TraceStatus status = null;

        try {
            status = trace.begin(message);

            // 로직 호출 ( 이부분만 자식이 구현하도록 템플릿 세팅 )
            T result = this.call();

            trace.end(status);
            return result;
        } catch (Exception   e) {

            trace.exception(status, e);
            throw e;

        }
    }

    protected abstract T call();
}
