package hello.aop.order.aop.member;

import hello.aop.order.aop.member.annotation.ClassAop;
import hello.aop.order.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ClassAop
@Component  // 스프링 빈 등록 위해
public class MemberServiceImpl implements MemberService {
    
    @Override
    @MethodAop("test value")
    public String hello(String param) {
        log.info("[MemberServiceImpl] hello 실행");
        return "OK";
    }

    public String internal(String param) {
        return "INTERNAL OK";
    }
}
