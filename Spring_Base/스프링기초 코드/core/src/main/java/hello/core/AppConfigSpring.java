package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigSpring {
    /*
    이렇게 @Autowired 는 등록된 Bean 들을 가져와서
    관리하는 컨테이너에 수동으로 연결시켜주는 방식
    @Configuration 빼고 아래처럼 @Autowired 로 연결시켜도 동일하게 관리 되긴한다! (그런데 그냥 Configuration 쓸 것 )
    @Autowired
    public MemberRepository memberRepository;

     */

    // new AnnotationConfigApplicationContext(AppConfigSpring.class) 가 호출 되는 순간
    // AppConfigSpring 에서 @Bean 어노테이션이 붙은 모든 메서드를 호출하여 등록 함
    // @Bean(name="memberServiceNew") 이런식으로 빈의 이름을 지정할수도 있다. 기본 디폴트는 메서드명과 동일
    // 그리고 내가 설정한 관계들을 분석하여 알아서 의존관계를 주입해준다
    @Bean
    public MemberService memberService() {
        System.out.println("memberService 생성자 호출");  // 실제로 한번만 호출 됨
        return new MemberServiceImpl(memberRepository());
   }

    @Bean
    public MemberRepository memberRepository() {

        System.out.println("memberRepository 생성자 호출");  // 실제로 한번만 호출 됨
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {

        System.out.println("orderService 생성자 호출");

        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

}
