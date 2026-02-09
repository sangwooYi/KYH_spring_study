package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

/*
        한번 쿠키 보내주면 유효기간동안
        Request-header 에 그 쿠키를 담아서 보내준다
        그걸 여기서 이렇게 확인 해 볼 수 있음.
        Cookie[] cc = request.getCookies();
        for(Cookie coo : cc) {
            System.out.println("쿠키ㅜ킼");
            System.out.println(coo.getName());
        }
*/

        // 응답코드 설정
        // HttpServletResponse 안에 상수값으로 상태코드들이 전부 정의되어있다.
        // 실제로 이 응답 코드에 따라 로직이 작용 됨 ex. SC_NO_CONTENT 로 보내면 (204) 성공이긴 한데,
        // 메시지 바디 없는채로 보내버린다.
        //redirect(response);   //리다이렉트 테스트

        response.setStatus(HttpServletResponse.SC_OK);

        // response-header
        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        // 캐시 비활성화 시키리면 아래 두 줄을 작성
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello");

        cookie(response);

        response.getWriter().write("하이");

    }

    private void content(HttpServletResponse response) {
        response.setHeader("Content-Type", "text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(3);   // 이건 알아서 사실 세팅 해 준다.
        // 캐시 비활성화 시키리면 아래 두 줄을 작성
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello");
    }

    private void cookie(HttpServletResponse response) {
        // 이렇게 하는것과 동일 아래 Cookie 클래스 이용해서 addCookie 해주면
        // 알아서 header 에 변환되어 넣어주는 것!
        //response.setHeader("Set-Cookie", "testCookie=thanks; Max-Age=600");

        Cookie cookie = new Cookie("testCookie", "thanks");
        cookie.setMaxAge(600);   // 쿠키 유효기간 600초 Max-Age 설정해주면 Expires (만료시간) 값을 자동으로 넣어준다!
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FOUND);
        // 리다이렉트 url 이렇게 헤더 Location 값에 지정해주면 된다.
        response.setHeader("Location", "/basic/hello-form.html");

        // 근데 실제로는 이렇게 그냥 한다.
        // 위에 두줄을 대체 함
        //response.sendRedirect("/basic/hello-form.html");
    }
}
