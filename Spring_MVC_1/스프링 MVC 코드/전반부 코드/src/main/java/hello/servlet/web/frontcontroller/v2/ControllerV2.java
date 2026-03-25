package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ControllerV2 {
    
    // url 링크 매핑해주는 역할
    // v1 과 다른점, 반환타입이 MyView
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
