package hello.servlet.domain.member;

import hello.servlet.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemberRepository {

    hello.servlet.domain.MemberRepository memberRepository = hello.servlet.domain.MemberRepository.getInstance();

    // @AfterEach 는 각 테스트 메서드 종료 후에 호출 되는 어노테이션
    // @BeforeEach 는 각 테스트 메서드 실행 전에 호출 되는 어노테이션
    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Member member = new Member("jisun", 28);

        // when
        Member saveMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findMember(saveMember.getId());

        Assertions.assertThat(saveMember).isSameAs(findMember);
    }

    @Test
    void findAll() {

        // given
        Member member1 = new Member("jisun", 28);
        Member member2 = new Member("sangwoo", 34);
        Member member3 = new Member("aimyon", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        // when
        List<Member> memberList = memberRepository.findAll();

        // then
        Assertions.assertThat(memberList.size()).isEqualTo(3);
        Assertions.assertThat(memberList).contains(member1, member2, member3);
    }
}
