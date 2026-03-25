package hello.aop.order.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;


/**
 *  내부호출 문제 대안3
 *  구조 변경 ( 여기선 구조를 분리! )
 *  -> 이게 가장 권장 됨!  이렇게 구조를 분리하는게 가능하다면!
 *  ( 그게 아니라면 LAZY 를 사용 ! )
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    private final InternalService internalService;

    public void external() {
        log.info("external 호출");
        internalService.internalService();
    }
/*
    public void internal() {
        log.info("internal 호출");
    }*/
}
