package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// urlPatterns 에 / 이거로 안시작하면 에러난다. ...
@WebServlet(name="responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        // application/json 은 아래처럼 utf-8 설정을 굳이 안해도 됨 ( 기본스펙이 utf-8 로 인식함 )
        // response.setCharacterEncoding("utf-8");

        HelloData helloData = new HelloData();
        helloData.setAge(30);
        helloData.setUserName("이상우");

        // JSON 을 JSON String 으로 바꾸어 줌
        String result = objectMapper.writeValueAsString(helloData);
        System.out.println(result);

        // .write() 는 int , String, char 만 처리해줌
        // 그 외에는 전부 .print() 나 .println() 으로 처리해 줘야 함!
        response.getWriter().write(result);
    }
}
