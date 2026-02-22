package hello.proxy.app.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController// 어쨌건 3.0버전부터는 컨트롤러면 @Controller나 @RestController 설정이 필요하다!
public class OrderControllerV2  {

    private final OrderServiceV2 orderService;

    public OrderControllerV2(OrderServiceV2 orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/v2/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "V2 OK";
    }

    @GetMapping("/v2/no-log")
    public String noLog() {
        return "V2 OK";
    }
}
