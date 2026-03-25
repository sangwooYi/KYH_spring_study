package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Slf4j  이거 쓰면 private final Logger log = LoggerFactory.getLogger(LogTestController.class); 생략 가능
@RestController
public class LogTestController {

    // 클래스명.class 는 클래스 자체를 그대로 넘겨주는 코드
    private final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @RequestMapping("/log-test")
    public String logTest() {

        String name = "Spring";
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        // 그냥 {} 만 해주면 알아서 순서대로 인자들 입력된다 다른것처럼 {0} 이런거 아님! (오히려 안됨!)
        log.warn(" warn log={} aa {}", name, name);
        log.error("error log={}", name);

        return "OK";
    }
}
