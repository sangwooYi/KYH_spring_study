package hello.core.scan.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        BeanA beanA = ac.getBean(BeanA.class);

        // 얜 org.assertj.core.api. 여기의 Assertions
        //Assertions.assertThat()

        // 이건 org.junit.jupiter.api 의 Assertions
        // 에러 체크할떄는 이거 사용할 것
        Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean(BeanB.class));

    }

    @Configuration
    @ComponentScan(
                                    // type 의 기본값은 FilterType.ANNOTATION !
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    // static 으로 해야 내부클래스 꺼내 쓸 수 있다!
    public static class ComponentFilterAppConfig {

    }
}
