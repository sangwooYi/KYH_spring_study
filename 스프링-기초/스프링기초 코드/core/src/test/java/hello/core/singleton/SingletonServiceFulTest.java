package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class SingletonServiceFulTest {


    @Test
    void statefulSingletonTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        SingletonFullTest instance = ac.getBean("singletonFullTest", SingletonFullTest.class);
        SingletonFullTest instance2 = ac.getBean("singletonFullTest", SingletonFullTest.class);

        instance.order("김치", 10000);
        instance2.order("고기", 200000);

        // 같은 인스턴스니까 당연히 변수를 따로 선언하더라도 필드가 공유된다.. (당연한거.. static이나 마찬가지인거)
        //System.out.println(instance.getPrice());

        Assertions.assertThat(instance).isSameAs(instance2);
    }


    @Configuration
    static class TestConfig {

        @Bean
        public SingletonFullTest singletonFullTest() {
            return new SingletonFullTest();
        }


    }
}
