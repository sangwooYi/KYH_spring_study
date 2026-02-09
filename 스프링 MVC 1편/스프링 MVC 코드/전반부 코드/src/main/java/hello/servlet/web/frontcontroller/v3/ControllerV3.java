package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import java.util.Map;

public interface ControllerV3 {
    
    // 현재 Process 는 HttpServlet 관련해서 정보를 가지고 있을 필요가 없다
    // 따라서 분리! ModelView 라는 별도의 모델을 사용
    ModelView process(Map<String, String> paramMap);

}
