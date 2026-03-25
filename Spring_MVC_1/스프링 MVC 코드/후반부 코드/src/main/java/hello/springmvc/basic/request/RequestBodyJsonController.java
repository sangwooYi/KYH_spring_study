package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    // JSON 파싱하는 고전 방법
    // 이거 외에 Json-Simple 활용하는 방법 존재
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        // inputStream 안에 꺼내쓸수있게 들어 있다. 인자로 inputStrea, 변환형태
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        // 고전적인 방법은 이렇게 String 형태를 ObjectMapper 로 꺼내오기
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

    }

    /**
     * RequestBody 로 일단 messageBody만 가져오기
     * 
     * @param messageBody
     * @throws IOException
     */
    @PostMapping("/request-body-json-v2")
    public ResponseEntity<String> requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}", messageBody);

        // 고전적인 방법은 이렇게 String 형태를 ObjectMapper 로 꺼내오기
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    /**
     * DTO 사용
     * @RequestBody 는 왠만하면 DTO 로 받자!!!
     * @ResponseBody 를 붙여주면 ( 아니면 그냥 클래스에 @RequestController) 알아서 바로 응답 처리가능ㅋ
     * 
     * 주의할점! @RequestBody 이거 생략하면 @ModelAttribute로 인식하기떄문에 못가져온다
     * @ModelAttribute 가 꺼내올 수 있는 값 -> Path Variable, 쿼리 파라미터, HTML 폼
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public ResponseEntity<HelloData> requestBodyJsonV3(@RequestBody HelloData helloData) {

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    /**
     * HttpEntity (  혹은 RequestEntity ) 로도 물론 쓸수 있다.
     * 근데 Body에서 꺼내오는 과정이 필요하니 그냥 @RequestBody 사용
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public ResponseEntity<HelloData> requestBodyJsonV4(RequestEntity<HelloData> hello) {

        HelloData helloData = hello.getBody();

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }
}
