package hello.advanced.trace.hellotrace;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloTraceV1Test {

    @Test
    void begin_end() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);

        TraceStatus nextLevStatus = trace.begin(status.getTraceId().createNextTracId().createNextTracId(), "hello-lev2");
        trace.end(nextLevStatus);
    }

    @Test
    void begin_exception() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException("테스트 예외 발생"));
    }

}