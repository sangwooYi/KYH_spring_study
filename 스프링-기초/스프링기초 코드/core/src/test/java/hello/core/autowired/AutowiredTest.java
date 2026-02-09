package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.bean.override.convention.TestBean;

import java.util.Optional;

public class AutowiredTest {

    @Test
    @DisplayName("Autowired 옵션 확인")
    void AutowiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);


    }

    static class TestBean {
        // 이렇게 required = false로 설정한 경우 자동 주입할 대상이 없으면 그냥 메서드 호출 자체가 안 됨
        @Autowired(required = false)
        public void setNoBean1(Member member) {
            System.out.println("member11 :  " + member);    // 호출 자체가 안됨
        }

        // 디폴트는 required = true
        // @Nullable 자동주입할 대상이 없으면 null 을 입력함
        @Autowired
        public void setNoBean2(@Nullable Member member) {
            System.out.println("member22 : " + member); // null
        }

        //  자동주입할 대상이 없으면 Optional.empty 가 입력 됨 ( NullPointException 을 방지해주는 자바 기본 제공 클래스 )
        @Autowired
        public void setNoBean3(Optional<Member> member) {
            System.out.println("member33 : " + member); // Optional.empty 
        }
    }

}
