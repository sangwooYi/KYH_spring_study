package hello.aop.proxyvs;

import hello.aop.order.aop.member.MemberService;
import hello.aop.order.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDiAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j

// JDK 동적프록시 적용 가능 ( 디폴트는 true (true면 항상 CGLIB로 생성))
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})
@SpringBootTest
@Import(ProxyDiAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    /**
     *  JDK 동적프록시로 생성시 Exception
     *  CGLIB 로는 가능
     *
     *  Why? 앞에 캐스팅 에러와 같은 원인으로써
     *  의존성 주읩 때 MemberServiceImpl 이 주입되는게 아니라, 프록시 클래스로 주입이 되려고 함! ( 이로인해 Exception 발생 )
     *  -> JDK 동적프록시의 경우 프록시 클래스가 MemberServiceImpl 로 캐스팅이 안되기 때문에!
     */
    @Test
    void go() {
        log.info("memberService class = {}", memberService.getClass());

        log.info("memberServiceImpl class = {}", memberServiceImpl.getClass());

        memberServiceImpl.hello("HELLO A");
    }
}

