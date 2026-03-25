package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseView1() {

        ModelAndView mv = new ModelAndView("response/hello");
        mv.addObject("data", "sangwoo");

        return mv;
    }

    /**
     * 주의할점!
     * @RestContrller 나, @ResponseBody 쓸때는
     * 이렇게 String 반환하는거 하면 뷰를 찾아주는게 아니라 그냥 이 String 을 HTTP 바디에 담아 응답처리해버림.. 주의하자
     * 
     * @param model
     * @return
     */
    @RequestMapping("/response-view-v2")
    public String responseView2(Model model) {

        model.addAttribute("data", "HeLLO ~~ :) ");

        return "response/hello";
    }
}
