package hello.advanced;

import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    // 싱글톤으로 등록됨! ( 이로인해 동시성 이슈 발생!! )
//    @Bean
//    public LogTrace logTrace() {
//        return new FieldLogTrace();
//    }

    @Bean
    public ThreadLocalLogTrace threadLocalLogTrace() {
        return new ThreadLocalLogTrace();
    }
}
