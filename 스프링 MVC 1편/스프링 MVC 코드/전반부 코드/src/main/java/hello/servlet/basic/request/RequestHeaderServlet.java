package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

// url-pattern 은 기본적으로 소문자, 단어 연결은 - 으로!
@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //printStartLiner(request);
        //printHeadersOld(request);
        //printAllHeaders(request);
        printHeaderUtils(request);
        printEtc(request);
    }
    // request 에 있는 Start-Line 정보
    private void printStartLiner(HttpServletRequest request) {
        System.out.println("--- REQUEST-LINE - start ---");
        System.out.println("request.getMethod() = " + request.getMethod()); //GET
        System.out.println("request.getProtocol() = " + request.getProtocol()); //HTTP/1.1
        System.out.println("request.getScheme() = " + request.getScheme());     // http  ( http https 이게 scheme )
        System.out.println("request.getRequestURL() = " + request.getRequestURL()); // http://localhost:8080/request-header  // URL 은 전체 출력
        System.out.println("request.getRequestURI() = " + request.getRequestURI());  // request-header      // URI 는 뒷부분만 출력 ( 쿼리 파라미터 전까지 )
        System.out.println("request.getQueryString() = " + request.getQueryString()); // 쿼리 파라미터 ( 쿼리 스트링이라고도 한다. )
        System.out.println("request.isSecure() = " + request.isSecure());   //https 사용 유무  https 사용중이면 이게 true
        System.out.println("--- REQUEST-LINE - end ---");
        System.out.println();
    }
    // 옛날 버전
    // 웹브라우저 Network 에서 request 쪽에 Header 있는거 그냥 다 보여준다.
    private void printHeadersOld(HttpServletRequest request) {
        System.out.println("==== Header Name Print Start ====");

        // 벡터 값들을 하나씩 처리해줄수 있는 클래스가 Enumeration
        Enumeration<String> headerNames = request.getHeaderNames();
        while ( headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            // 이런식으로 전부 찍어볼 수 있음
            System.out.println(headerName +  " = " + request.getHeader(headerName));
        }
    }

    private void printAllHeaders(HttpServletRequest request) {
        System.out.println("==== Header Name Print Start ====");
        // 요즘에는 이렇게 함수형 메서드 이용해서 출력한다고 함!
        request.getHeaderNames().asIterator().forEachRemaining(
                headerName -> System.out.println(headerName +  " = " + headerName));

        // 아래처럼 Header 정보중 원하는거 찍어서 가져올수도 있음
        // Map 처럼 이름값으로 가져와야 한다!
        request.getHeader("host");

        System.out.println("====== Header END ======");

    }

    private void printHeaderUtils(HttpServletRequest request) {
        System.out.println("--- Header 편의 조회 start ---");
        System.out.println("[Host 편의 조회]");
        System.out.println("request.getServerName() = " + request.getServerName()); //Host 헤더 서버네임
        System.out.println("request.getServerPort() = " + request.getServerPort()); //Host 헤더  포트
        System.out.println();

        System.out.println("[Accept-Language 편의 조회]");
        // locales 안에 locale 정보들이 들어있다. ( 복수로 들어있으므로 아래처럼 출력 할 것 )
        request.getLocales().asIterator().forEachRemaining(locale -> System.out.println("locale = " + locale ));

        // locales 에는 언어별로 가중치가 부여되어있는데, Accept-Language 값에
        // 이중에 가장 가중치가 높은 언어를 locale 쪽에 자동으로 담아준다 따라서 그냥 getLocale 쓰면 됨!
        System.out.println("request.getLocale() = " + request.getLocale());
        System.out.println();

        System.out.println("[cookie 편의 조회]");
        if (request.getCookies() != null) {
            // getCookies 로 Cookie 정보를 가져올 수도 있다
            for (Cookie cookie : request.getCookies()) {
                // name, value 값을 각각 가져올 수 있음
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();

        System.out.println("[Content 편의 조회]");
        // GET 방식일때는 보통은 Content-Type 자체가 의미가 없다 ( http 메시지가 없으므로 )
        System.out.println("request.getContentType() = " + request.getContentType());
        System.out.println("request.getContentLength() = " + request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());
        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();
    }

    // HTTP 메시지의 정보는 아니다.
    private void printEtc(HttpServletRequest request) {
        System.out.println("--- 기타 조회 start ---");
        System.out.println("[Remote 정보]");
        // 나랑 네트워크 커넥션 맺어진 정보 (즉 나한테 요청온 서버에 대한 정보)
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost()); 
        System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr());
        System.out.println("request.getRemotePort() = " + request.getRemotePort());
        System.out.println();
        // 내 서버의 정보
        System.out.println("[Local 정보]");
        System.out.println("request.getLocalName() = " + request.getLocalName()); //
        System.out.println("request.getLocalAddr() = " + request.getLocalAddr()); //
        System.out.println("request.getLocalPort() = " + request.getLocalPort()); //
        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }
}
