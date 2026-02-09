package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        // @Component 안붙여도 이렇게 class 로 전달해주면 알아서 이 클래스 빈에 등록해 줌!
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PrototypeBean.class);

        // 요청 올때마다 새로 생성된다..
        PrototypeBean prototypeBean1 = applicationContext.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = applicationContext.getBean(PrototypeBean.class);
        // 따라서 당연히 주소도 다름
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2 );

        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        // 빈을 해제할 필요가 있으면 아래처럼 수동으로 호출 해 줘야 한다.
        prototypeBean1.destroy();

        applicationContext.close();
        //Assertions.assertThrows()
    }

    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean destroy");
        }


    }
}
