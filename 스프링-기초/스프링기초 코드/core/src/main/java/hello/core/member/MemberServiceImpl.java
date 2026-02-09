package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;


    /*
        // setter 주입 방식 ( 수정자 주입 방식 )
        // 선택,변경 가능성이 있는경우는 이 방식 사용
        // 가장 권장되는건 생성자 주입 방식
        @Autowired
        public void setMemberRepository(MemberRepository memberRepository) {
            this.memberRepository = memberRepository;
        }

    */

    // @Component로 설정시에는 이렇게 @Autowired 통해서 의존관계 설정을 추가로 해줘야 한다!
    // @Autowired 역할 ac.getBean("memberRepository", MemberRepository.class) 를 해주는 역할
    // 컨테이너의 빈을 꺼내서 연결시켜주는 역할
    // 생성자 주입 방식
    // 근데 이 클래스처럼 생성자가 한개만 존재하는 경우는 @Autowired 안써도 알아서 붙여준다! (근데 그냥 생략하지말자.)
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
