package hello.aop.order.aop.exam;

import hello.aop.order.aop.exam.annotation.Retry;
import hello.aop.order.aop.exam.annotation.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 *  이번 예제처럼 필요한 프록시 로직을 어노테이션과, @Aspect 통해 작성하고, 이때 대상 지정은
 * @annotation 포인트컷을 통해서 적용할 메서드에 적용하는 방식 유용하게 사용 된다!
 * 주의할 점은 재실행 로직 만들 때는 반드시 최대 반복 횟수를 지정 해 주어야 한다! (가능하면 디폴트 설정을 해 두자)
 *
 */
@Slf4j
@Repository
public class ExamRepository {

    private static int seq = 0;

    /**
     *  테스트용 5회당 1회 에러 발생
     */
    @Trace
    @Retry(value = 4)       // value 생력하면 디폴트값인 3이 적용, 선언시 value()로 했기 때문에 value 가 param 명이 된 것
    public String save(String itemId) {
        seq++;
        log.info("[ExamRepository save] itemId = {} / seq = {}", itemId, seq);
        if (seq % 5 == 0) {
            throw new IllegalStateException("예외 발생!");
        }
        return "OK";
    }

}
