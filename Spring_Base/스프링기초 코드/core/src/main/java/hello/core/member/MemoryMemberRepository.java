package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Component("service") // 이렇게 이름 지정이 가능한데 만약에 지정한 이름이 겹치면 .ConflictingBeanDefinitionException 발생
@Component
public class MemoryMemberRepository implements MemberRepository{
    // 임시 메모리 저장소 ( Map 사용 )
    private static Map<Long, Member> store = new HashMap<Long, Member>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
