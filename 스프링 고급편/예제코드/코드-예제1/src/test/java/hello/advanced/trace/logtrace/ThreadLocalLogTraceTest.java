package hello.advanced.trace.logtrace;


import hello.advanced.trace.hellotrace.TraceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class ThreadLocalLogTraceTest {

    ThreadLocalLogTrace trace = new ThreadLocalLogTrace();

    @Test
    void begin_end_level() {
        TraceStatus status1 = trace.begin("hello111");
        TraceStatus status2 = trace.begin("helo2222");

        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception_level() {
        TraceStatus status1 = trace.begin("hello111");
        TraceStatus status2 = trace.begin("helo2222");

        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

}