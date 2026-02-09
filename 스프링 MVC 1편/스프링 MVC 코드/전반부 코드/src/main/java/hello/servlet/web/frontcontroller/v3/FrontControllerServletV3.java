package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// /front-controller/v2/* 는 이런형태로 들어오는 모든경로는 우선 여기를 거침
@WebServlet(name="frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        // 다형성 적용
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String curUri = request.getRequestURI();
        ControllerV3 controller = controllerMap.get(curUri);

        // 해당경로 없으면 404
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // service와 다른 디테일한 로직이 들어가면 그냥 분리하는게 좋다!
        Map<String, String> paramMap = this.createParamMap(request);

        ModelView modelView = controller.process(paramMap);
        
        // View Resolver도 service와 다른 별도의 역할이므로 분리
        MyView myView = this.viewResolver(modelView.getViewName());
        // 모델 정보를 넘겨줘야 한다.
        myView.render(modelView.getModel(), request, response);

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
