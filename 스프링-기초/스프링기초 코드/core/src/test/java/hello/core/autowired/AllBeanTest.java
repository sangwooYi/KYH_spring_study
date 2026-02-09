package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBean() {
        // 이렇게 AppConfig 여러개 인자로 넘길수도 있음
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DiscountService.class, AutoAppConfig.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "지선", Grade.VIP);

        int discountPrice =  discountService.discount(member, 10000, "fixDiscountPolicy");
        int rateDiscountPrice = discountService.discount(member, 100000, "rateDiscountPolicy");

        Assertions.assertThat(discountPrice).isEqualTo(1000);
        Assertions.assertThat(rateDiscountPrice).isEqualTo(10000);
    }

    static class DiscountService {

        // 스프링이 자동으로 이 Map 과 List 에 해당 클래스 하위에 존재하는 모든 빈을 알아서 다 넣어준다.
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;

        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;

            System.out.println("policyMap : " + policyMap);
            System.out.println("policyList : " + policyList);
        }
        // Map 이나 List를 통해서 이렇게 여러가지 빈을 가지고 매핑 시킬수도 있다!
        public int discount(Member member, int i, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, i);
        }
    }
}
