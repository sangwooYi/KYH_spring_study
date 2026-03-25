package hello.springmvc.basic.requestmapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger logger = LoggerFactory.getLogger(MappingController.class);

    @RequestMapping(value="/hello-basic", method = RequestMethod.GET)
    public String helloBasic() {
        logger.info("hello Basic Start");
        return "HELLO BASIC~";
    }

    /**
     * @RequestMapping(value= , method= ) 형태의 축약형
     *
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @PatchMapping
     * @DeleteMapping
     */
    // PathVariable 사용
    // ( 주의 . ?key=value&key2=value2 형태의 쿼리파라미터랑은 구분하자! <- 이건 request.getParameter() 로 꺼내온다. )
    // @PathVariable({변수명}) 형태로 인자에 선언해줘야함.
    // 위 변수명값이랑 value값에 설정한 {} 값이랑 같은 명이어야 함!
    // 근데 PathVariable 에 설정한 변수명과, 인자의 변수명이 같은경우 @PathVariable String userId 이런식으로 생략 가능!
    @GetMapping(value="/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String userId) {

        logger.info("MappingPath Variable = {}", userId);

        return "OK";

    }

    // 당연히 PathVariable 여러개 사용 가능
    // 위에서 말한것처럼 같은 변수명 쓰면 아래처럼 @PathVariable String userId 이렇게 생략해서 사용 가능
    // 물론 @PathVariable("userId") String userId 이게 기본 형태
    // @RequestParam 과 @PathVariable 과 쿼리 파라미터 각자 확실히 구분해 두자
    @GetMapping(value="/mapping/users/{userId}/orders/{orderId}")
    public String mappingPathMultiple(@PathVariable String userId,
                                      @PathVariable String orderId,
                                      // 이렇게 RequestParam 있는거 전부 받을 수도 있고
                                      @RequestParam MultiValueMap<String, String> paramMap,
                                      // 이렇게 지정해서 뽑아올 수 도 있다.
                                      @RequestParam("find") String find) {
        logger.info("userId = {}, orderId = {} Success", userId, orderId);
        logger.info("paramMap = {}" , paramMap);
        logger.info("find = {}", find);
        return String.format("userId = %s, orderId = %s", userId, orderId);
    }

    /**
     * 
     * 필수 쿼리파라미터 지정
     * params= 에 key=value 형태로 지정하면 해당 변수의 지정한 값이 아니면 호출이 안된다!
     * params 에 설정한 변수명, 설정값이 모두 일치해야함 ( 하나라도 다르면 400 Bad Request 응답 떨어짐 )
     * find!=test 면 해당 변수에 이 값이 오면 안된다는 의미! (이렇게 배제 조건도 설정 가능 )
     *
     */
    @GetMapping(value="/mapping-param", params = {"mode=debug", "find!=test"})
    public String mappingParam(HttpServletRequest request, HttpServletResponse response) {

        String mode = request.getParameter("mode");
        String find = request.getParameter("find");

        logger.info("mode={} find = {}", mode, find);

        return "OK";
    }

    /**
     * 필수 헤더 지정
     * 
     * 필수 Parameter 지정과 유사하며,
     * params 처럼 headers 로 지정함 사용법 완전 동일
     * 얘는 하나라도 지정한 조건에 안맞으면 404 Not Found 응답
     *
     */
    @GetMapping(value="/mapping-header", headers = {"mode=debug", "purpose!=test"})
    public String mappingHeader(HttpServletRequest request, HttpServletResponse response) {

        // 이 방식도 까먹지 말자!
        request.getHeaderNames().asIterator().forEachRemaining(
                headerName -> logger.info("var = {}, value = {}", headerName, request.getHeader(headerName))
        );
        return "OK~";
    }

    /**
     *
     * Content-Type 지정
     * 아래 형태로 다양하게 지정 가능
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     *
     * 그리고 MediaType 이란 클래스로 스프링에서 상수값들 정의해 놓았으므로 필요하면 이걸 갖다 쓸것!
     *
     * 지정한 형태에 안맞춘 경우 415 Unsupported Media Type 예외처리 응답
     */
    @GetMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingContent(HttpServletRequest request, HttpServletResponse response) {

        logger.info(request.getContentType());

        return "OK";
    }

    /**
     *  Accept 타입 지정
     *  
     *  produce= 값으로 지정하며
     *  consumes 와 지정 방법 완전 동일
     */
    @GetMapping(value = "/mapping-accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public String mappingAccept(HttpServletRequest request, HttpServletResponse response) {

        request.getHeaderNames().asIterator().forEachRemaining(
                headerName -> logger.info("headerName = {}, value = {} ", headerName, request.getHeader(headerName))
        );

        return "OK";
    }
}

