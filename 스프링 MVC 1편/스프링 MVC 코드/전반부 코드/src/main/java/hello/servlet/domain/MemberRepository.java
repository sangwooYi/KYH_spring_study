package hello.servlet.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {
    
    // 멤버 ID 를 키값으로 관리
    private Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    private MemberRepository() {

    }
    public static MemberRepository getInstance() {
        return instance;
    }
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    public Member findMember(Long id){
        return store.get(id);
    }
    public List<Member> findAll() {
        // store 는 안건드리면서 데이터를 다루고 싶어서
        return new ArrayList<>(store.values());
    }
    public void clearStore() {
        store.clear();
    }

}
