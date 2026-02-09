package hello.core.beanConflict;

import hello.core.AppConfigSpring;
import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanConflictTest {

    @Test
    void testBeanConflict() {

        // 예제, Component에 FixDiscount, RateDiscount 를 둘다 등록해둔 상황
        // 따라서 빈이 중복되어 UnsatisfiedDependencyException 발생 !
        // 에러 발생시점이 getBean이 아닌 이 컨테이너 등록하는 단계에서 발생한다!
        // 근데 이떄 필드명을 원하는 구현체로 세팅해주면 해당 구현체 클래스로 알아서 주입해준다.!
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        DiscountPolicy discountPolicy = ac.getBean(DiscountPolicy.class);
        /*
        Assertions.assertThrows(UnsatisfiedDependencyException.class,
                () ->new AnnotationConfigApplicationContext(AutoAppConfig.class));
                */


    }

}
