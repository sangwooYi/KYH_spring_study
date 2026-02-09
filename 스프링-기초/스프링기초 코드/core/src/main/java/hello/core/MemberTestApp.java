package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class MemberTestApp {
    public static void main(String[] args) {
        
        // appConfig 싱글톤 적용
        /*
        AppConfig appConfig = AppConfig.getInstance();

        MemberService memberService = appConfig.memberService();
        */

        // ApplicationContext 역할 → 내가 인자로 넘겨준 @Configuration 클래스를 이용해서
        // .getBean 으로 빈 생성 요청을 받으면 @Configuration 클래스로부터 적절한 빈을 생성 및 반환받아 넘겨주는 역할
        // Annotation 기반의 Java 설정을 이용해서 만드는게 AnnotationConfigAPplicationContext 클래스
        // GenericXmlApplicationContext 가 xml 로 설정할 수 있게 도와주는 친구.
        // xxxApplicationContext 클래스가 존재하는 식이다.
        // 내가 임의로 만들 수도 있다..
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigSpring.class);
        // 메서드명            // 반환 타입
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);


        Member testMember1 = new Member(1000000L, "상우", Grade.NORMAL);
        Member testMember2 = new Member(1000001L, "아이묭", Grade.NORMAL);
        Member testMember3 = new Member(1000002L, "아이미", Grade.VIP);

        memberService.join(testMember1);
        memberService.join(testMember2);
        memberService.join(testMember3);

        Member fMember = memberService.findMember(1000002L);

        System.out.println("findMember Name : " + testMember3.getName());
        System.out.println("findMember find Name : " + fMember.getName());
        System.out.println("findMember find Name : " + "ㅁaa");
    }
}
