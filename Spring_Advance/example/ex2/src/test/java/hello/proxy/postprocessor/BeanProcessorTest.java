package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class BeanProcessorTest {

    @Test
    void basicConfig() {

        // 이게 스프링 컨테이너
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanProcessorConfig.class);

        // A 에서 B로 바꿔치기 됨
        // "beanA" 라는 이름으로 B 객체가 등록되는 상황이 됨!
//        B b = applicationContext.getBean("beanA", B.class);

        B b = applicationContext.getBean("beanA", B.class);
        b.helloB();
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            log.info("beanName = {}", beanName);
        }
        Assertions.assertThat(b.getClass()).isEqualTo(B.class);

        Assertions.assertThatThrownBy(() -> applicationContext.getBean(A.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Configuration
    static class BeanProcessorConfig {

        // name 지정 안해주면 디폴트는 메서드명으로 됨
        @Bean(name="beanA")
        public A a() {
            return new A();
        }

        @Bean
        public AtoBPostProcessor atoBPostProcessor() {
            return new AtoBPostProcessor();
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

    // BeanPostProcessor 를 implements 하여 구현하면 된다.
    // default 인터페이스는, 기본 메서드가 구현이 된 인터페이스로,
    // 기본 메서드에 대한 구현이 강제되지 않는다!
    static class AtoBPostProcessor implements BeanPostProcessor {

        // postProcessBeforeInitialization
        // -> @PostConstructor 같은 초기화 메서드 호출 전
        
        // postProcessAfterInitialization
        // @PostConstructor 같은 초기화 메서드 호출 후 ~ 빈 저장소 등록 전
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName = {}, beans = {}", beanName, bean);

            // 바꿔치기
            if (bean instanceof A) {
                return new B();
            }
            return bean;
        }
    }
}
