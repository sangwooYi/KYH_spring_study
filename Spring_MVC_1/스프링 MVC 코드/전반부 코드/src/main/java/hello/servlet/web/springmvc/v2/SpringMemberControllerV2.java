package hello.servlet.web.springmvc.v2;

import hello.servlet.domain.Member;
import hello.servlet.domain.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// 공통 url 경로를 갖고있는경우 이렇게 하나로 묶을 수 있다
// 이게 기본!
@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    // 그냥 이거써도 된다 @GetMapping
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getMembers(HttpServletRequest request, HttpServletResponse response) {
        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members", members);

        return mv;
    }
    // @PostMapping 써도 된다.
    @RequestMapping(value="/save", method = RequestMethod.POST)
    public ModelAndView saveMember(HttpServletRequest request, HttpServletResponse response) {

        String userName = request.getParameter("userName");

        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(userName, age);
        memberRepository.save(member);

        ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("member", member);

        return mv;
    }

    @RequestMapping(value = "/new-form", method = RequestMethod.GET)
    public ModelAndView getNewForm() {
        return new ModelAndView("new-form");
    }
}
