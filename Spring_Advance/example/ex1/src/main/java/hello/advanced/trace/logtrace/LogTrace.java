package hello.advanced.trace.logtrace;

import hello.advanced.trace.hellotrace.TraceStatus;

/**
 * LogTrace 인터페이스
 * begin : 로그 실행
 * end : 로그 정상 종료
 * exception : 로그 예외 발생
 */
public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);

}
