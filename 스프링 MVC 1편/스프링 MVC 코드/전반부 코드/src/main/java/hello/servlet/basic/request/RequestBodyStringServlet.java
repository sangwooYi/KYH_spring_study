package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

    @WebServlet(name="requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 메시지 바디의 내용을 바이트 코드로 가져옴
        ServletInputStream inputStream = request.getInputStream();
        // 바이트 코드를 String으로 바꾸는 방법 반드시 두번째 인자에 인코딩 타입 지정해 줘야 함
        String inputStr =  StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        // 단순 텍스트는 Content-Type 이 test/plain
        System.out.println("=== 단순 텍스트 전송 ===");
        System.out.println(inputStr);
        response.getWriter().write("ok");
    }
}
