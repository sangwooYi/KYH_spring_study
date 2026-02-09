package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        // 지원 여부 판단 방법 instanceof 사용!
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        if (!(handler instanceof ControllerV4)) {
            return null;
        }
        ControllerV4 controller = (ControllerV4) handler;

        Map<String, String> paramMap = this.createParamMap(request);

        Map<String, Object> model = new HashMap<>();
        String viewName = controller.process(paramMap, model);

        // 34 ~ 39번째줄이 어댑터의 역할!
        ModelView modelView = new ModelView(viewName);
        // 모델 넣어줘야 한다!
        modelView.setModel(model);

        return modelView;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        // 존재하는 request의 parameter 전부 가져오는 방법 꼭 기억!
        // 따라서 이 예제코드에서는 GET 쿼리 파마리터 혹은 HTML 폼으로 오는 POST 요청에 대해서만 처리 가능!
        request.getParameterNames().asIterator().forEachRemaining(
                paramName -> paramMap.put(paramName, request.getParameter(paramName))
        );
        return paramMap;
    }
}
