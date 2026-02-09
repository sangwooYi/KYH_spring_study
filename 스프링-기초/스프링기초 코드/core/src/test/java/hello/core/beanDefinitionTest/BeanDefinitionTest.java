package hello.core.beanDefinitionTest;

import hello.core.AppConfigSpring;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigSpring.class);
    GenericXmlApplicationContext acXml = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("AnnotationConfigApplicationContext 빈 네임 전부 살펴보기")
    void findApplicationName() {

        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {

            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println(beanDefinitionName);
                System.out.println(beanDefinition);
                System.out.println("============================================");
            }
        }
    }

    @Test
    @DisplayName("GenericXmlApplicationContext 이용해서 빈 네임 전부 살펴보기")
    void findApplicationNameXmlVersion() {

        String[] beanDefinitionNames = acXml.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {

            BeanDefinition beanDefinition = acXml.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println(beanDefinitionName);
                System.out.println(beanDefinition);
                System.out.println("============================================");
            }
        }
    }

}
