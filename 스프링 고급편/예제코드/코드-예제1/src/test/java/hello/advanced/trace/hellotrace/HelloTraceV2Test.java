package hello.advanced.trace.hellotrace;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloTraceV2Test {

    @Test
    void begin_end() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status = trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status.getTraceId(), "hello22");
        trace.end(status2);
        trace.end(status);
    }

    @Test
    void begin_exception() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException("테스트 예외 발생"));
    }

}