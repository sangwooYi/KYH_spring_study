package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    // 스프링 빈 컨테이너는 객체 생성 후 -> 의존관계 주입 사이클이기 떄문에
    // 초기화 단계는 의존관계 주입이 완료 된 이후에 진행되어야 한다! (생각해보면 당연한 것)
    @Test
    public void lifeCycleTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient networkClient = ac.getBean(NetworkClient.class);

        // 빈 컨테이너 종료하는 메서드
        ac.close();

    }

    @Configuration
    static class LifeCycleConfig {

        // 이렇게 Bean 에서 initMethod, destroyMethod를 지정할 수 있다. 
        // 의존관계 주입 후에 initMethod 호출 / destroyMethod 호출 후에 빈 컨테이너 해제
        // 라이프 사이클 잘 기억하자!
        // "" 안에 들어오는건 메서드명
        //@Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

    }
}
