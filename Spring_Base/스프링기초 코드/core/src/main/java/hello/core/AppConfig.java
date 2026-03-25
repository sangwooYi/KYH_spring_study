package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 이렇게 분리를 해준 덕분에 Client 쪽을 건드릴 필요가 없어짐! ( DIP 원칙 준수 가능 )
public class AppConfig {

    // 싱글톤으로 혹시 만들거면
    // 1. 자기 자신을 private static 멤버변수로 선언 후 스스로를 생성자로 초기화
    // 2. 생성자 부분에서 초기화 할 부분 작성, 생성자는 private 로 작성
    // 3. 자기 자신 멤버변수 호출할 getter를 static으로 생성하여 그 getter 로만 참조하도록 작성
    private static AppConfig appConfig = new AppConfig();

    private MemberService memberService;
    private OrderService orderService;
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    private AppConfig() {

        this.memberService = this.memberService();
        this.orderService = this.orderService();

    }

    public MemberService memberService() {
        if (memberService == null) {
            return new MemberServiceImpl(this.memberRepository());
        }
        return memberService;
    }

    public OrderService orderService() {
        if (orderService == null) {
            return new OrderServiceImpl(this.memberRepository(), this.discountPolicy());
        }
        return orderService;
    }

    public MemberRepository memberRepository() {
        if (memberRepository == null) {
            return new MemoryMemberRepository();
        }
        return memberRepository;
    }

    public DiscountPolicy discountPolicy() {
        if (discountPolicy == null) {
            // 이제 만약에 구현체가 바뀌면 이렇게 AppConfig쪽만 바꿔주면 된다.
            return new RateDiscountPolicy();
            //return new FixDiscountPolicy();
        }
        return discountPolicy;
    }

    public static AppConfig getInstance() {
        return appConfig;
    }
}
