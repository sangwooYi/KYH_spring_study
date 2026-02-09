package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceImplTest {

    @Test
    void createOrder() {

        // 생성자 주입 방법을 쓰면
        // 이렇게 단위 테스트 짜는것도 안전하고, 용이해진다.
        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        memoryMemberRepository.save(new Member(1L, "name", Grade.VIP));
        Order order = orderService.createOrder(1L, "itemA", 100000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
