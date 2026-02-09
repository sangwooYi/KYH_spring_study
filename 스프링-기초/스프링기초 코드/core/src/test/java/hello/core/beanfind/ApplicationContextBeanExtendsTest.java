package hello.core.beanfind;

import hello.core.AppConfigSpring;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBeanExtendsTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 호출시에 자식이 둘 이상 있어도 오류 발생")
    void findBeanByParentClass() {

        // 부모 클래스로 조회시에 자식이 둘이상이면 중복때와 마찬가지로
        // NoUniqueBeanDefinitionException  예외발생!!
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));

    }

    @Test
    @DisplayName("부모 타입으로 호출시에 자식이 둘 이상 이상이면 중복떄와 마찬가지로 이름으로 조회")
    void findBeanByParentClassByName() {

        // 부모 클래스로 조회시에 자식이 둘이상이면 중복때와 마찬가지로
        DiscountPolicy discountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);

        //  Assertions.assertThat(a).isInstanceOf(b.class)  a 는 b의 인스턴스가 맞는지 체크하는 로직;
        Assertions.assertThat(discountPolicy).isInstanceOf(RateDiscountPolicy.class);

    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType() {

        // 이렇게 타입으로만 조회시에, 동일한 타입으로 2개 이상 나오면 그냥
        // NoUniqueBeanDefinitionException  예외발생!
        // Object로 조회시 모든 빈을 그냥 다 가져온다
        //Map<String,Object> memberRepositoryMap = ac.getBeansOfType(Object.class);
        Map<String, DiscountPolicy> discountPolicyMap = ac.getBeansOfType(DiscountPolicy.class);
        for (String key : discountPolicyMap.keySet()) {
            // 테스트 할때나 이렇게하지 실제로는 실전에서는 서버에 올릴때 이런 출력물은 가능하면 적진 말자..
            System.out.printf("key = %s value = %s\n", key, discountPolicyMap.get(key));
        }
        //Assertions.assertThat(memberRepositoryMap.size()).isEqualTo(2);
    }
    
    @Configuration
    static class TestConfig {

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }

    }
}
