package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name="requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    // JSON 파싱을 위해서는
    // Jackson 라이브러리 필요
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletInputStream inputStream = request.getInputStream();
        // JSON 에서 이렇게 가져올 때 JSON String 이라고 부른다. ( JSON 인데 String 타입 상태로 있는 데이터 )
        String messageBody =  StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        System.out.println(messageBody);

        // 두번째 DTO 클래스명 써주면 됨 (알아서 넣어 줌! )
        // DTO 의 변수명과 , JSON 에서 보내주는 parameter 명이 완전히 동일해야함 ( 안그러면 파싱에러 발생 )
        // 이 예제에서는 userName / age 로 보내져야함 ( HelloData 에서 정의를 그렇게 했으므로 )
        // 나중에는 Controller 에서 @RequestBody 어노테이션 통해서 바로 DTO 통해 받을 수 있다! (실전에서는 보통 이거로)
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        System.out.println("userName = " + data.getUserName());
        System.out.println("age = " + data.getAge());


        response.getWriter().write("Success");
    }
}
