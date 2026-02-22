package hello.proxy.app.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
// @RestController 는 @ResponseBody 를 가지고 있는 컨트롤러 어노테이션!
@RestController// 어쨌건 3.0버전부터는 컨트롤러면 @Controller나 @RestController 설정이 필요하다!
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;

    public OrderControllerV3(OrderServiceV3 orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/v3/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "V3 OK";
    }

    @GetMapping("/v3/no-log")
    public String noLog() {
        return "V3 OK";
    }
}
