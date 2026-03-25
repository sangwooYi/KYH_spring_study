package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    AppConfig appConfig = AppConfig.getInstance();

    MemberService memberService = appConfig.memberService();

    @Test
    void join() {

        // given
        Member member = new Member(1L, "아이묭", Grade.VIP);

        // when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then
        Assertions.assertThat(member).isEqualTo(findMember);    // 둘이 진짜 같은값인지 체크해주는 친구

    }

}
