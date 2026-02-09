package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Enumeration;

// 이거 말고 URL Pattern 적용하려면 @Controller 써야함
// 굳이 여기서 하려면 url Patterns 를 경로/* 식으로 아스테리스크 사용해서 잡고
// service 메서드에서 request.getRequestURI() 로 가져와서 다 if - else 분기처리 해야함. .ㅡㅡ
// WebServlet 은 무조건 service 메서드로 들어간다.
@WebServlet(name="requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("시작 요~~");
        System.out.println("=== 요즘 버전 람다식 활용 ===");
        // 이렇게 람다식 활용하여 전체 출력 가능
        request.getParameterNames().asIterator().forEachRemaining(
                // 아래 형식으로 쓸 수 있다.
                parameterName -> System.out.println(parameterName + " =  " + request.getParameter(parameterName))
        );

        // 옛날 버전은 이거
        System.out.println("=== 옛날 버전 Enumeration 사용 ===");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            System.out.println(parameterName + " = " + request.getParameter(parameterName));
        }

        System.out.println("=== 단일 Parameter 조회 ===");
        // 하나만 뺴오려면 .getParameter() 이용
        // 중요한게 이 쿼리파라미터는
        // Get 방식의 쿼리 파라미터와 HTML Form 을 통해 보내는 POST 형식의 파라미터 모두 받아 줄 수 있다.
        // HTML Form 에 담겨진 데이터들을 보낼 때 쿼리 파라미터처럼 name1=value1&name2&value2 이런식으로 보내주기 때문!
        String height = request.getParameter("height");
        System.out.println("height =  " + height);

        // 만약에 쿼리 파라미터의 변수명이 겹치는경우 ( ex userName 으로 여러명이 들어온 경우 )
        // 왠만하면 이거 쓸일이 없어야 하나, 하나의 파라미터에 2개 이상의 값이 물려있는 경우 사용
        // 이런경우 그냥 getParameter 쓰면 그냥 첫번째 값을 반환 ( 에러는 안 남 )
        /*
        String[] userNames = request.getParameterValues("username");
        for (String userName : userNames) {
            System.out.println(userName);
        }*/
        response.getWriter().write("thank you");
    }
}
