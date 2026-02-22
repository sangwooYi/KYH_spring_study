package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template;

    @GetMapping("/v5/request")
    public String request(@RequestParam String itemId) {

        return template.execute("OrderServiceV5.request() 실행!!", () -> {
            orderService.orderItem(itemId);
            return "OK";
        });
    }

}
