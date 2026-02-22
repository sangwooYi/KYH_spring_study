package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.*;


/**
 *  Controller 를 인터페이스로 선언할때 아래처럼 해 주어야 함
 */
@RestController     // 3.0 버전 이상부터는 그냥 @RestController 를 선언해 주면 된다.
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();

}
