package hello.springmvc.basic.request;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

// @Slf4j 해주면 Logger log = LoggerFactory.getLogger(클래스명) 이 코드 굳이 안써도 로그 사용 가능해짐!
// 로거의 변수명은 log 로 세팅 되어 있음
@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request, HttpServletResponse response,
                          HttpMethod httpMethod, Locale locale,
                          // @RequestHeader 아무것도 지정안해주면 모든 헤더 정보가 담김
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          // 아래처럼 특정 명칭 지정해주면 이 헤더 key 값에 해당하는 value 값만 가져옴
                          @RequestHeader("host") String host,
                          @CookieValue(value="myCookie", required = false) String cookie) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);

        log.info("=== header Map ===");
        // MultiValueMap 순회하는 방법
        // MultiValueMap 은 하나의 key 에 여러 value 를 받을 수 있는 자료구조
        // .add("keyA", "val1"), .add("keyA", "val2") 이런식으로 같은 key로 다른 값을 또 add 하면
        // 다른 Map 처럼 덮어써지는게 아니라 array 형태에 value에 추가 됨! ( 값이 하나라도 array에 들어있음 )
        // 애초에 인터페이스 를 보면 extends Map<K, List<V>> 이렇게임! ( 인터페이스끼리 상속도 extends 임 )
        headerMap.forEach((key, value) -> log.info("key={}, value={}", key, value));
        log.info("=== header Map ===");

        log.info("host={}", host);
        log.info("cookie={}", cookie);

        // 서버에서 쿠키 추가하는 방법
        Cookie nCookie = new Cookie("myCookie", "vale");
        response.addCookie(nCookie);

        return "OK";
    }
}
