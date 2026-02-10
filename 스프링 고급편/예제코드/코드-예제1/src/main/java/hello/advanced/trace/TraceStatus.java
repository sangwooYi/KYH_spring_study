package hello.advanced.trace;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Trace 상태 체크용 오브젝트
 * -> For 소요시간 체크 ( 종료시각-시작시각 )
 */
@Getter
@RequiredArgsConstructor
public class TraceStatus {

    private final TraceId traceId;
    private final Long startTimeMs;
    private final String message;

}
