package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class RequestBodyStringController {

    @PostMapping("/request-body-stringv1")
    public String requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException  {

        // HTTP 메서드 바디 정보 직접 받는 방법
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        return messageBody;
    }

    /**
     * InputStream 바로 받아서 처리 가능
     * @param inputStream
     * @return
     * @throws IOException
     */
    @PostMapping("/request-body-stringv2")
    public String requestBodyStringV2(InputStream inputStream) throws IOException  {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        return messageBody;
    }


    /**
     * HTTP Method 컨버터
     * HttpEntity 스프링 클래스 사용
     * @param httpEntity
     * @return
     * @throws IOException
     */
    @PostMapping("/request-body-stringv3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

        // 이렇게 바로 바디에 있는 값 꺼낼수 있게 해준다 ( 대신 String 형태 )
        String messageBody = httpEntity.getBody();

        log.info("messageBody={}", messageBody);

        // Json Simple 사용하여 JSON 파싱하기 ( 가장 쉬운 방법 )
        // ParseException 예외 처리 해 줘야 함
        // -> 메서드에서 throws 하던지, try-catch 로 감싸주던지 해야 함
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(messageBody);
        } catch (ParseException e) {
            log.error("Parse Exception ! message = {}", e.getMessage());
        } catch (ClassCastException e) {
            log.error("ClassCastException ! message = {}", e.getMessage());
        }

        if (jsonObject != null) {
            log.info("userName = {}", jsonObject.get("userName"));
        }

        return new HttpEntity<>(messageBody);
    }

    /**
     * HTTP Method 컨버터
     * HttpEntity 를 상속받은 RequestEntity ResponseEntity 많이 쓰임
     * @param httpEntity
     * @return
     * @throws IOException
     */
    @PostMapping("/request-body-stringv3-1")
    public ResponseEntity<String> requestBodyStringV31(RequestEntity<String> httpEntity) throws IOException {

        // 이렇게 바로 바디에 있는 값 꺼낼수 있게 해준다 ( 대신 String 형태 )
        String messageBody = httpEntity.getBody();

        log.info("messageBody={}", messageBody);

        // ResponseEntity는 이렇게 메시지 바디랑, HTTP Status 값을 같이 전송해 줄 수 있다.
        return new ResponseEntity<>(messageBody, HttpStatus.OK);
    }

    /**
     * @RequestBody
     *
     * 그냥 DTO 사용해서 @RequestBody를 쓰자!
     * 이게 가장 최선의 방법
     * 그리고 @RequestBody 는 굳이 DTO 가 아닐지라도
     * 단순 텍스트도 처리 된다.
     * 즉  HTTp 메서드 바디 데이터 다룰 때는 그냥 @RequestBody 사용
     *
     */
    @RequestMapping("/request-body-string-v4")
    public ResponseEntity<String> requestBodyStringV4(@RequestBody String messageBody) {

        log.info("messageBody={}", messageBody);

        return new ResponseEntity<>(messageBody, HttpStatus.CREATED);
    }
}
