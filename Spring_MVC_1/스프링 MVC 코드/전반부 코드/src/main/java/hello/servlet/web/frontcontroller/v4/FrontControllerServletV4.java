package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// /front-controller/v2/* 는 이런형태로 들어오는 모든경로는 우선 여기를 거침
@WebServlet(name="frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        // 다형성 적용
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String curUri = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(curUri);

        // 해당경로 없으면 404
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // service와 다른 디테일한 로직이 들어가면 그냥 분리하는게 좋다!
        // paramMap에는 요청에서 들어온 param 들이 전부 담겨있고
        Map<String, String> paramMap = this.createParamMap(request);

        // ModelView 클래스를 굳이 안거쳐도 됨!
        Map<String, Object> model = new HashMap<>();

        // 이 작업 이후엔 model 에 응답에 필요한 값들이 담겨져 있다.
        String viewName = controller.process(paramMap, model);

        // viewPath
        MyView myView = this.viewResolver(viewName);

        // 모델 정보를 넘겨줘야 한다.
        myView.render(model, request, response);

    }

    // 이렇게 아예 사이즈가 커지는 애들은 보통 분리하는게 좋다! ( 하나의 메서드는 하나의 역할을 부여하는걸 일반적으로 )
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        // 존재하는 request의 parameter 전부 가져오는 방법 꼭 기억!
        // 따라서 이 예제코드에서는 GET 쿼리 파마리터 혹은 HTML 폼으로 오는 POST 요청에 대해서만 처리 가능!
        request.getParameterNames().asIterator().forEachRemaining(
                paramName -> paramMap.put(paramName, request.getParameter(paramName))
        );
        return paramMap;
    }

    private MyView viewResolver(String viewName) {
        // 이게 View Resolver 역할
        String viewPath = String.format("/WEB-INF/views/%s.jsp", viewName);
        MyView myView = new MyView(viewPath);

        return myView;
    }

}
