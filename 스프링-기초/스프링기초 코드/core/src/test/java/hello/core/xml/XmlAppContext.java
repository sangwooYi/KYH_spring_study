package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class XmlAppContext {

    @Test
    void xmlAppContext() {
        // 이런식으로 xml 파일을 불러와서 컨테이너에 등록 해준다.
        // 이 방식도 이해할 수 있어야 한다. (그냥 XMl 파일로 등록하냐 @Configuration 클래스를 이용해서 등록하냐 차이밖에 없음 )
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
