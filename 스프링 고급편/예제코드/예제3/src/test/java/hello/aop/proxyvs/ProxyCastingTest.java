package hello.aop.proxyvs;

import hello.aop.order.aop.member.MemberService;
import hello.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // 어차피 false 가 디폹트임!
//        proxyFactory.setProxyTargetClass(false);    // 인터페이스 있는 클래스가 target이므로 디폴트는 JDK 동적 프록시

        // 인터페이스로 캐스팅은 성공
        MemberService proxy = (MemberService) proxyFactory.getProxy();

        // 구체 클래스로는 안 됨 ( 사실 당연한거임! MemberService 인터페이스의 자식개념으로 프록시가 만들어진거므로 )
        // ClassCastException 에러 남
        // 람다식 우변에 표현이 단순식이 아닐때는 {} 사용하기!
        Assertions.assertThatThrownBy(() -> {
                   MemberServiceImpl test = ( MemberServiceImpl ) proxyFactory.getProxy();
                })
        .isInstanceOf(ClassCastException.class);
    }

    @Test
    void CGLIBProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);    // true 로 할경우, 인터페이스 있는 target일지라도 CGLIB 로 프록시 생성

        // CGLIB 는 구체클래스 기반으로 생성하기에 MemberServiceImpl 로도 캐스팅이 가능!
        MemberService proxy = (MemberService) proxyFactory.getProxy();
        MemberServiceImpl test = ( MemberServiceImpl ) proxyFactory.getProxy();
    }
}
