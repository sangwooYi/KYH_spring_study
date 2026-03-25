package hello.advanced.app.v4;

import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepository;
    private final ThreadLocalLogTrace trace;

    public void orderItem(String itemId) {

        // 제네릭에 void를 넣을수 없으므로 대체클래스로서 Void 가 존재 ( placeholder class 라고 부름 )
        // void 에 래퍼클래스가 아닌 별도의 void 대체용 클래스이다! (이부분 주의) 
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            // 따라서 아래처럼 반환타입이 Void 여야 하며 void 타입설정 불가
            // + null 을 반환해 주어야한다
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };

        template.execute("OrderServiceV4.orderItem()");

    }

}
