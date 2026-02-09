package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
//@Primary //중복 빈이 발생할때 @Primary 통해서 빈 우선순위 설정해 주는것이 가능하다!
//@Qualifier("mainDiscountPolicy")
public class RateDiscountPolicy implements  DiscountPolicy{

    private int discountRate = 10;

    @Override
    public int discount(Member member, int price) {

        int resPrice = price;

        if (member.getGrade() == Grade.VIP) {
            resPrice = price * discountRate / 100;
        }
        return resPrice;
    }
}
