package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 템플릿 메서드 기본예제1
 */
@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        this.logic1();
        this.logic2();
    }

    /**
     *  템플릿 메서드 적용 Ver
     */
    @Test
    void templateMethodV1() {
        // 의존성 역전! DI ( 추상화 인터페이스에 의존하도록 설정 )
        AbstractTemplate template1 = new SubClassLogic1();
        AbstractTemplate template2 = new SubClassLogic2();

        template1.execute();
        template2.execute();

    }

    /**
     *  템플릿 메서드 익명 내부클래스로 구현 ver
     */
    @Test
    void templateMethodV2() {
        // 익명 클래스
        // 익명 클래스이기 때문에 람다로는 구현 X 
        // ( 람다를 쓰려면 인터페이스에 @FunctionalInterface 를 붙여 구현 가능하도록 세팅 해야 함 )
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스로직 1 실행~");
            }
        };

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스로직 2 실행~");

            }
        };

        template1.execute();

        template2.execute();
    }

    /**
     *   핵심 비즈니스 로직에
     *   시간 측정 부가기능이 섞여있는 상태
     */
    private void logic1() {
        long startTime = System.currentTimeMillis();    // 밀리초

        // 비즈니스 로직 ( 있다고 가정 )
        log.info("비즈니스 로직1 실행");

        long endTime = System.currentTimeMillis();

        long resultTime = endTime-startTime;
        log.info("resultTime = {}", resultTime);

    }

    private void logic2() {
        long startTime = System.currentTimeMillis();    // 밀리초

        // 비즈니스 로직 ( 있다고 가정 )
        log.info("비즈니스 로직2 실행");

        long endTime = System.currentTimeMillis();

        long resultTime = endTime-startTime;
        log.info("resultTime = {}", resultTime);

    }
}

