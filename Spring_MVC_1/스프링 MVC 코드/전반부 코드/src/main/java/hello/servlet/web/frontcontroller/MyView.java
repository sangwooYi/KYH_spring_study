package hello.servlet.web.frontcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyView {

    String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    // 공통 렌더링 함수
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(this.viewPath);
        dispatcher.forward(request, response);
    }

    // 공통 렌더링 함수
    // 오버로딩  (오버라이딩이랑 헷갈리지말자,
    // 오버로딩은 같은 메서드명으로 매개변수 다르게해서 여러개 선언하는것 (Over Loading)
    // 오버라이딩은 상속 과정에서 메서드를 재정의 하는것  (Overriding <- 뜻 자체가 덮어쓰다라는 뜻)
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 참조 데이터 형이므로 이렇게 그냥 해도 됨
        this.setModelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(this.viewPath);
        dispatcher.forward(request, response);
    }

    private void setModelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        // 람다식 굳이 안써도 되긴 함
        // 근데 자료구조 처리할 때는 람다식이 요긴하게 쓰이니까 익숙하게 해두자!
        // 현재 갖고있는 model 정보를 전부 request에 꽂아준다.
        model.forEach((key, value) -> request.setAttribute(key, value));

        // 람다식 안써도 아래처럼 key 이용해서 순회해도 됨!
        /*
        Set<String> keys = model.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String curKey = iterator.next();
            request.setAttribute(curKey, model.get(curKey));
        }
        */
    }

}
