package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 자동빈 vs 자동빈 Component 이름 겹치는 경우 .ConflictingBeanDefinitionException 발생
// 자동빈 vs 수동빈 => 수동빈이 우선적으로 오버라이딩 됨
public class AutoAppConfigTest {

    @Test
    void basicScan(){

        // @Qualifier 는 이부분에서 빈 컨테이너 주입 시 우선순위 설정이 가능하도록 해주는 부분!
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        // @SpringBootApplication 에 @ComponentScan이 걸려있다! 따라서 사실 그냥 별도로 Config 클래스 없어도 됨!
        //ApplicationContext ac = new AnnotationConfigApplicationContext(CoreApplication.class);
        String[] beanKeys = ac.getBeanDefinitionNames();
        for (String key : beanKeys) {
            System.out.println(key);    // 알아서 등록 되었다!

        }
        MemberService memberService = ac.getBean(MemberService.class);

        // @Qualifier 만으로는 이렇게 getBean 할때 해결이 안된다.
        // 이때는 이름으로 꺼내줘야 함! ac.getBean(DiscountPolicy.class); 만 쓰면 빈 중복 에러 남! (당연한 것! )
        DiscountPolicy discountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);

        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);

        // @Bean 과 @Component의 이름 충돌시키면 이렇게 @Bean 즉 수동빈이 우선권을 가진다.
        // 근데 @SpringBootApplication 붙어있는 파일에서 부트 실행하면 이런경우도 그냥 에러 발생시켜버림
        //Assertions.assertThat(discountPolicy).isInstanceOf(FixDiscountPolicy.class);
    }
}
