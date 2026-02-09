package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


// @Controller 어노테이션 역할
// 1. @Component 를 갖고 있으므로, 스프링 컨테이너 빈에 등록해주는 역할
// 2. RequestMappingHandlerMapping 에 대상으로 등록해 주는 역할
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
