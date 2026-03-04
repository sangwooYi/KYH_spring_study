package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class BasicTest {

    @Test
    void basicConfig() {

        // 이게 스프링 컨테이너
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);

        // A는 빈으로 등록 됨
        A a = applicationContext.getBean("beanA", A.class);
        a.helloA();

        // B는 없음
        // 이거 문법 기억해 두자
        Assertions.assertThatThrownBy(() -> applicationContext.getBean("beanB", B.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Configuration
    static class BasicConfig {

        @Bean(name="beanA")
        public A a() {
            return new A();
        }

//        @Bean(name="beanB")
//        public B b() {
//            return new B();
//        }

    }

    static class A {
        public void helloA() {
            log.info("helloA!!");
        }
    }

    static class B {
        public void helloB() {
            log.info("helloB!!");
        }
    }
}
