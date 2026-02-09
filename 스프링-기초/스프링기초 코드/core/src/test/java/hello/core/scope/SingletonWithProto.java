package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithProto {

    @Test
    void prototypeFind() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        /*
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        prototypeBean1.addCount();
        prototypeBean2.addCount();

        Assertions.assertThat(prototypeBean1.getCount()).isSameAs(1);
        Assertions.assertThat(prototypeBean2.getCount()).isSameAs(1);
        */

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);

        System.out.println(clientBean1.logic());
        System.out.println(clientBean2.logic());
    }

    // 방법 3. Provider 사용 ( javax 자바 표준 코드임)
    static class ClientBean {

        //  ObjectProvider<PrototypeBean> prototypeBeanObjectProvider 사용하면 알아서 프로토타입 빈을 생성해 준다!
        private Provider<PrototypeBean> provider;

        @Autowired
        public ClientBean(Provider<PrototypeBean> provider) {
            this.provider = provider;
        }

        public int logic() {
            // 이렇게 하면 prototypeBeanObjectProvider.getObject(); 처럼 알아서 프로토타입 빈 생성해 준다
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }

    }

    /*
    방법 2 ObjectProvider 사용 (알아서 찾아준다. 근데 스프링에 의존적)
    static class ClientBean {

        //  ObjectProvider<PrototypeBean> prototypeBeanObjectProvider 사용하면 알아서 프로토타입 빈을 생성해 준다!
        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;

        @Autowired
        public ClientBean(ObjectProvider<PrototypeBean> prototypeBeanObjectProvider) {
            this.prototypeBeanObjectProvider = prototypeBeanObjectProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }

    }
    */
    /*
    static class ClientBean {

        //private final PrototypeBean prototypeBean;
        private ApplicationContext applicationContext;

        // Prototype은 프로토타입 스코프 빈이나,
        // 이렇게 싱글톤 빈에 의존성 주입이 되면 싱글톤 컨테이너에 포함되어 버린다.. 그래서 싱글톤 관리되어버린다..
        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            //System.out.println("CLient Bean 생성자 호출");
            //this.prototypeBean = prototypeBean;
            applicationContext = new AnnotationConfigApplicationContext(PrototypeBean.class);
        }

        public int logic() {
            // 피하려는 방법중 가장 무식한게 이렇게 매번 새로 생성해주는것..
            PrototypeBean prototypeBean = applicationContext.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }*/


    @Scope("prototype")
    static class PrototypeBean {
        private int count;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PostConstruct = " + this);
        }

        // 프로토 타입 빈이니까 어차피 자동 호출 안 됨
        @PreDestroy
        public void destroy() {
            System.out.println("Destroy");
        }

    }
}
