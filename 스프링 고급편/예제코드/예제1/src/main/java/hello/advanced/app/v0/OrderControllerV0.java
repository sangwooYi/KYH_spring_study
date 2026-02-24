package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderControllerV0 {

    private final OrderServiceV0 orderService;

    @GetMapping("/v0/request")
    public String request(@RequestParam String itemId) {
        log.info("itemId = {}", itemId);
        orderService.orderItem(itemId);
        return "OK";        // 그냥 문자 그대로를 HTTP BODY 에 담아준다 (@RestController 때문! )
    }

}
