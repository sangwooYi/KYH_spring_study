package hello.core.singleton;


import hello.core.AppConfig;
import hello.core.AppConfigSpring;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


// 가끔씩 말도안되는 에러 뜰때는 그냥 메뉴 -> 캐쉬 무효화해서 그냥 캐쉬지우고 다시실행 하자
public class SingletonTest {

    @Test
    @DisplayName("싱글톤 테스트")
    void checkSingleton() {
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();

    }

    @Test
    @DisplayName("스프링 컨테이너 확인")
    void spingContainer() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigSpring.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        Assertions.assertThat(memberService1).isSameAs(memberService2);

    }
}
