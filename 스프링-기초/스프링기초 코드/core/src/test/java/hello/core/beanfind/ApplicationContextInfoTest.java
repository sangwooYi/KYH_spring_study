package hello.core.beanfind;

import hello.core.AppConfigSpring;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigSpring.class);

    @Test
    @DisplayName("모든 빈 출력")
    public void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames(); // 등록된 모든 빈 이름을 반환해줌

        for (String beanName : beanDefinitionNames) {
            Object curBean = ac.getBean(beanName);
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanName);
            System.out.println(beanDefinition.getRole());

            System.out.println("name : " + beanName + " bean : " + curBean);
        }
    }
    @Test
    @DisplayName("모든 어플리케이션 빈만 출력")
    public void findAllApplicationBean() {
        // 컨테이너에 등록된 모든 빈들의 이름을 찾아줌
        String[] beanDefinitionNames = ac.getBeanDefinitionNames(); // 등록된 모든 빈 이름을 반환해줌

        for (String beanName : beanDefinitionNames) {
            // 각 빈의 이름으로 빈의 정보를 찾아볼 수 있음
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanName);
            Object curBean = ac.getBean(beanName);
            // ROLE_APPLICATION 이 내가 혹은 외부 라이브러리가 등록한 빈인것
            // ROLE_APPLICATION = 0
            // ROLE_SUPPORT = 1
            // ROLE_INFRASTRUCTURE = 2     // 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("name : " + beanName + " bean : " + curBean);
            }

        }
    }

}
