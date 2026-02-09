package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// @ComponentScan 은 자동으로 컴퍼넌트 등록해주는 친구
// @Comonent 붙은 클래스들을 알아서 스캔해서 빈 등록해줌!
// 그 다음에 의존관계에 맞게 @Autowired 생성자에 세팅해 주면 컨테이너가 자동으로 의존관계 주입해 준다.
@Configuration
@ComponentScan(
        // 혹시 제외 할 대상은 이렇게 설정을 별도로 할 수 있다.
        // 이 설정은 Annotation 기준으로 Configuration 어노테이션 붙은 클래스들은 제외 하겠다는거 ( 테스트로 만든것들이 많다보니..)
        // @Configuration 어노테이션에 애초에 @Component 어노테이션이 포함되어 있다!
        // 실전에서 이 어노테이션 기준으로 제외할일은 없음..
        // basePackages 이용 탐색 시작위치 세팅이 가능함 ( 해당 패키지 하위의 파일들만 체크하고 끝냄 ! )
        // basePackageClasses 처럼 아예 classes 로 세팅할수 도있음
        // basePackages / basePackageClasses 명시를 안한 경우에는 @ComponentScan 클래스가 있는 패키지 기준으로 하위 파일 다 뒤짐
        // 우리 코드에서는 hello.core 패키지 하위 파일 전부 체크하는 식! ( 이게 디폴트 )
        // 따라서 그냥 굳이 명시하지말고 ComponentScan 어노테이션 설정할 Config 클래스를 내 프로젝트 최상단에 두는 방식을 권장!
        //basePackages = "hello.core.member",
        //basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)

)
public class AutoAppConfig {

    // 이렇게 수동빈과 자동빈 (@Component) 의 이름이 겹치면 수동빈이 (@Bean) 우선권을 가진다!
    /*
    @Bean("memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }*/

    /*
    @Bean("rateDiscountPolicy")
    DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }*/
}
