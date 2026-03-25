package hello.servlet.web.springmvc.old;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
// @Controller 어노테이션 나오기 전에 초창기 방식 ( 우리 v3 버전이랑 거의 동일 )
// 스프링 빈 컨테이너에 등록해 줘야 한다.
// 내가 등록한 빈 이름이 경로이며, 해당 경로로 HTTP 요청이 들어오면 이 컨트롤러가 자동으로 호출 됨
// 현재 HandlerMapping 의 핸들러 찾는 우선순위
// 0순위가 @RequestMapping ( 요새는 다 이거 쓴다. )  ->  RequestMappingHandlerMapping
// 1순위가 여기서 작성한 스프링 빈 이름이 경로인 경우 ( @Component({경로}) 형태! ) ->    BeanNameUrlHandlerMapping

// 참고 HandlerAdapter 의 경우는 우선순위가 아래와 같음
// 0순위 @RequestMapping -> RequestMappingHandlerAdapter (  @RequestMapping 을 사용한 클래스여야 함 )
// 1순위 HttpRequestHandler -> HttpRequestHandlerAdapter  ( HttpRequestHandler 를 구현한 클래스여야 함 )
// 2순위가 여기서 작성한 예전 Controller 인터페이스 -> SimpleControllerHandlerAdapter  ( Controller 인터페이스를 구현한 클래스여야 함 )
// RequestMappingHandlerAdapter 랑  SimpleControllerHandlerAdapter 는 소스코드 까보면 우리가 직접 만든 어댑터 클래스와 로직이 동일하다
// ( support 메서드로 핸들러 지원여부 판단, 그 이후에는 핸들러 정보 넘겨받아 ModelAndView 반환해주는 handle 메서드 )
@Component("/springmvc/old-controller") // 스프링 빈 이름을 경로로 설정
public class OldController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 따로 설정한게 없으므로 이렇게 구체적인 경로까지 지정해줘야 해당 view 가 렌더링 된다.
        // ViewResolver 를 설정해 줘야 우리가 원하는 형태로 구현이 됨!
        // 하는 방법은 resources 의 application.properties 에서
        // spring.mvc.view.prefix 랑 (경로) spring.mvc.view.suffix (확장자) 값을 세팅해주면 됨
        // 혹은 ServletApplication 에 InternalResourceViewResolver 를 @Bean 어노테이션 사용해서 구현해주면 됨
        // 위에서 application.properties 설정해주면 스프링이 해주는게 InternalResourceViewResolver 구현해주는 거임!
        // ModelAndView mv = new ModelAndView("/WEB-INF/views/new-form.jsp");

        return new ModelAndView("new-form");
    }
}
