package hello.servlet.web.springmvc.v1;

import hello.servlet.domain.Member;
import hello.servlet.domain.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SpringMemberListControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    // 해당 경로로 요청이 들어오면 이 process 가 자동으로 호출 된다
    // 메서드명은 아무거나 해도된다. @RequestMapping 의 경로를 찾아서
    // 해당 경로의 요청이 있는 경우 이 클래스를 자동으로 호출해 주는 것임!
    @RequestMapping("/springmvc/v1/members")
    public ModelAndView process(HttpServletRequest request, HttpServletResponse response) {

        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        // key-value 형태로 추가하는 식
        mv.addObject("members", members);

        return mv;
    }
}
