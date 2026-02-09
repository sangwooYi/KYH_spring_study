package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {
    
    // 현재 Process 는 HttpServlet 관련해서 정보를 가지고 있을 필요가 없다
    // 따라서 분리! ModelView 라는 별도의 모델을 사용

    // 아래 주석 만드는 방법!
    // 메서드 선언 한 후에 /** + 엔터 하면 알아서 만들어짐!
    /**
     *
     * @param paramMap
     * @param model
     * @return
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);

}
