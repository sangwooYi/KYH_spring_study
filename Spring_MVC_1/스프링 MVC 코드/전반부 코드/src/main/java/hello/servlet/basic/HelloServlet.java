package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 서블릿 설정
@WebServlet(name="helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    // HttpServletRequest 랑 HttpServletResponse 는 서버측에서 각각 클라이언트 요청 / 클라이언트에 응답을 사용
    // HttpRequest 랑 HttpResponse 는 클라이언트 측에서 서버에 요청 보낼 떄 / 서버로부터 응답을 받을 때 사용
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");

        // 쿼리 Parameter 이렇게 그냥 Map 에서 값 가져오듯이 쓸 수 있다.
        String userName = request.getParameter("userName");
        System.out.println("url = " + request.getRequestURL()); // 쿼리 parameter 부분 전까지 보내 준다.

        // 헤더 정보는 딱히 대소문자 구분 X
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        // 이부분이 화면에 뿌려지는 text
        response.getWriter().write("hello " + userName);
    }
}

