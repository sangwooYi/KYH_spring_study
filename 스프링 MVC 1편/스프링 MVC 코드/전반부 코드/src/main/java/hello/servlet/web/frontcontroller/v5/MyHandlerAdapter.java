package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface MyHandlerAdapter {

    // 해당 핸들러를 지원해주는지 여부를 판단
    boolean supports(Object handler);

    // supports 메서드가 참, 즉 지원하고 있다고 판단 된 핸들러 호출, 결과로 ModelView 를 반환
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException, IOException;

}
