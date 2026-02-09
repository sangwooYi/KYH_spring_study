package hello.servlet.web.frontcontroller;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ModelView {
    // 뷰의 논리적 이름
    private String viewName;
    
    // 뷰 모델 ( 모델은 뷰에 뿌려질 데이터 객체 )
    private Map<String, Object> model = new HashMap<>();

    // 생성자
    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
