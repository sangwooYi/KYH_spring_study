package hello.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderRepository {

    public String save(String itemId) {
        log.info("[OrderRepository] save 실행");

        if (itemId.equals("ex")) {
            throw new IllegalStateException("[OrderRepository] save 예외 발생!");
        }
        return "OK";
    }

}
