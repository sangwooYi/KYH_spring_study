package hello.advanced.app.v4;

import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;
    private final ThreadLocalLogTrace trace;

    @GetMapping("/v4/request")
    public String request(@RequestParam String itemId) {

        // 익명클래스 선언할 때 인자에 생성자에 넘겨줄 인자도 넘겨줘야 함
        AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "OK";
            }
        };

        return template.execute("OrderServiceV4.request() 실행!!");
    }

}
