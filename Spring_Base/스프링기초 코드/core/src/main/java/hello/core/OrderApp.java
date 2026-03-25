package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {

        // 이제 생성자 주입 및 연결을 AppConfig 에 넘기면서 아래처럼 관심사 분리가 가능해짐
        // 싱글톤으로 변경
        //AppConfig appConfig = AppConfig.getInstance();
        //MemberService memberService = appConfig.memberService();
        //OrderService orderService = appConfig.orderService();

        // 스프링 버전 ( AppConfig 정보를 이렇게 아래처럼 선언하면 알아서 의존성 주입을 해준다. )
        // AppConfig 에 설정한 뒤 ApplicationContext를 이용해서 컨테이너에 등록해줘야 함
        // 그다음에 이 컨테이너에서 계속 가져다 쓰면 된다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigSpring.class);
                                    // 메서드명            // 반환 타입
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "상우", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(1L, "노트북", 1000000);
        
        // toString() 오버라이딩 덕에 가능한 부분 
        // toString 오버라이딩 안해주면 그냥 주소값 출력 됨
        System.out.println("이번에 order = " + order);
    }
}
