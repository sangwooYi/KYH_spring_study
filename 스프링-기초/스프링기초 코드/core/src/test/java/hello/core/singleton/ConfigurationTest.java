package hello.core.singleton;

import hello.core.AppConfigSpring;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationTest {

    @Test
    @DisplayName("Configuration 싱글톤 테스트")
    void printConfigurationTest() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigSpring.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemoryMemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        Assertions.assertThat(memberRepository1).isSameAs(memberRepository2);

        System.out.println(memberRepository);
        System.out.println(memberRepository1);
        System.out.println(memberRepository2);  // 셋다 그냥 같은 놈이다.    ( @Configuration 의 역할 )

    }

    @Test
    void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigSpring.class);
        AppConfigSpring bean = ac.getBean(AppConfigSpring.class);

        System.out.println("AppConfigSpring = " + bean.getClass());     // 클래스명이 class hello.core.AppConfigSpring$$SpringCGLIB$$0 이런식이다.. 클래스명이 바뀌었다!
                                                                        // ( @Configuration 의 역할 !!! )
                                                                        // @Configuration 안붙이면 빈에는 등록되는데 싱글톤 보장이 안된다. 그냥 내 클래스가 그대로 등록됨..

    }

}
