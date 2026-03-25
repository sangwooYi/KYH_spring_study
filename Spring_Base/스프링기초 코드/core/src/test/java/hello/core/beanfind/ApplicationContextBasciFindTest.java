package hello.core.beanfind;

import hello.core.AppConfigSpring;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasciFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigSpring.class);

    @Test
    @DisplayName("이름으로 조회")
    void findBeanByName() {

        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }

    @Test
    @DisplayName("타입으로 조회")
    void findBeanByType() {

        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }

    @Test
    @DisplayName("구체 클래스로 조회")
    void findBeanByName2() {

        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }

    @Test
    @DisplayName("없는 빈 명으로 조회")
    void findBeanByNameError() {
        // NoSuchBeanDefinitionException 예외 발생
        //MemberService memberService = ac.getBean("memberServic11e", MemberService.class);
        // 오히려 내가 지정한 Exception이 나와야 성공하는 테스트 케이스
        // 내가 의도한 Exception 이 나오는지 테스트 코드 짜는 static 메서드
        assertThrows(NoSuchBeanDefinitionException.class, () ->
                ac.getBean("memberServic11e", MemberService.class));

    }
}
