package hello.aop.exam;

import hello.aop.order.aop.exam.ExamService;
import hello.aop.order.aop.exam.aop.RetryAspect;
import hello.aop.order.aop.exam.aop.TraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({TraceAspect.class, RetryAspect.class})  // Aspect 를 적용하려면 Aspect 클래스를 Import 해주어야 함!!
@SpringBootTest
public class ExamTest {

    @Autowired
    ExamService examService;

    @Test
    void test() {
        examService.request("TEST");
    }
}
