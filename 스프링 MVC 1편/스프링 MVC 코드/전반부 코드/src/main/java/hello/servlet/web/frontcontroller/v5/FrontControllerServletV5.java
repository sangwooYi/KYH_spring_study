package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // private Map<String, ControllerV3> controllerMap = new HashMap<>(); 기존 코드에서 어댑터 패턴 적용 차이
    // handler 목록

    private final Map<String, Object> handlerMap = new HashMap<>();
    
    // adapter 목록
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        // 이렇게 메서드 분리하는 부분도 잘 고민해보자!
        initHandlerMap();
        initHandlerAdapters();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 현재 요청 URI 에 따라 필요한 handler 정보 가져옴
        Object handler = getHandler(request);
        
        // 없으면 404 반환
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter handlerAdapter = getHandlerAdapter(handler);
        // 어댑터를 통해 ModelView 를 반환 받는 구조!
        ModelView modelView = handlerAdapter.handle(request, response, handler);

        String viewName = modelView.getViewName();
        MyView myView = this.viewResolver(viewName);

        // model 도 같이 반환해 줘야 함
        myView.render(modelView.getModel(), request, response);
    }

    private void initHandlerMap() {
        // v3 컨트롤러
        // 경로에 따른                           // 핸들러
        handlerMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // v4 컨트롤러
        handlerMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        // 지원되는 핸들러 어댑터 추가
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {

        for (MyHandlerAdapter adapter : handlerAdapters) {
            // 인자로 들어온 handler 가 지원되는 adapter면 해당 adapter 반환
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을수 없습니다. 입력 handler 정보 = " + handler);
    }

    private MyView viewResolver(String viewName) {
        // 이게 View Resolver 역할
        String viewPath = String.format("/WEB-INF/views/%s.jsp", viewName);
        MyView myView = new MyView(viewPath);

        return myView;
    }

}
