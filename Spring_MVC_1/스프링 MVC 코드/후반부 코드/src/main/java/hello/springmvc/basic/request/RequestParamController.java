package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;


/**
 *  경로에 /{userID} 형식으로 쓰는 Path Variable 은 @PathVariable
 *  쿼리 파라미터 ( 경로에 ?key=value&key2=value2&.. 식으로 적어주는 거) 는 @RequestParam
 *   HTML Form 으로 전달해주는 경우도 쿼리파라미터와 동일한 형태로 오므로 @RequestParam
 *   HTTP 메서드 통해서 오는 경우 ( 대부분  JSON ) 은 @RequestBody
 *
 */
@Slf4j
@RestController
public class RequestParamController {

    /**
     * request 객체에서 직접 꺼내는 방식
     * @param request
     * @param response
     */
    @RequestMapping("/request-param-v1")
    public String requestParamV1(HttpServletRequest request, HttpServletResponse response) {

        // 있는거 다 조회하는거 ( 쿼리 파라미터, HTML Form 으로 전달된 값들이 전부 담긴다 )
        request.getParameterNames().asIterator().forEachRemaining(
                paramName -> log.info("key = {}, value = {}", paramName, request.getParameter(paramName))
        );

        // 특정 Parameter 찍어서 조회하는 법
        String userName = request.getParameter("userName");
        log.info("userName = {}", userName);

        return "Success!!";
    }

    /**
     * 스프링에서 지원하는
     * @RequestParam 사용
     * @RequestParam("userId") 이렇게 지정한 경우, required 기본값은 true ,
     * 따라서 이 변수 없으면 400 Bad Request 예외 발생함
     * 만약에 필수값이 아니라면 @RequestParam(value = "userId", required = false) 이렇게 required 설정을 반드시 해줘야 함
     * 필수값인 경우에는 그리고 defaultValue 설정까지 해주는게 바람직하다.
     */
    @RequestMapping("/request-param-v2")
    public String userRequestParam(HttpServletRequest request, HttpServletResponse response,
                                   // MultiValueMap 으로도 받을수 있고 Map으로도 받을 수 있다!
                                   @RequestParam Map<String, String> paramMap,
                                   @RequestParam(value="aqe", defaultValue = "36") String age,
                                   @RequestParam(value = "userId", required = false) String userId) {

        log.info("paramMap = {}", paramMap);
        log.info("userId = {}", userId);

        // 주의할 점! 실제 Request Param 안에 age가 없는 경우에
        // 실제로는 값은 없는것! 따라서 이렇게 직접 꺼내보면 null 이다.
        log.info("age value Direct check = {}", request.getParameter("age"));
        // 다만 이렇게 사용할때 그냥 내가 설정한 defaultValue 값을 대체값으로 꽂아주는 것일 뿐
        log.info("age = {}", age);

        return "OK";
    }

    /**
     * 요청 Parameter 명과 일치하게 선언하고, 자바의 기본 type ( String, int, boolean ) 인 경우
     * 아예 @RequestParam 자체를 생략해도 알아서 매칭해줌
     * 근데 유지보수를 위해 생략해도 @RequestParam userName 이정도 생략까지만 사용할 것!
     *
     */
    @RequestMapping("/request-param-v4")
    public String userRequestParamSimple(String userName, int age) {

        log.info("userName = {}", userName);
        log.info("age = {}", age);

        return "OK";
    }

    /**
     * defaultValue 에서 주의사항은
     * 빈문자 "" 인 경우에도 빈값으로 판단하여 defaultValue를 적용 해 줌!
     * defaultValue 없는 경우에는 String 은 "" 빈문자인 경우에도 값이 있다고 판단해서 에러가 안난다.
     * ex. &userId=  그냥 이렇게 보낸 경우에도 에러가나는게 아니라 "" 값으로 넘어옴
     * 근데 defaultValue 있으면 이경우에도 default 값으로 대체해 줌
     */
    @RequestMapping("/request-param-default")
    public String userRequestDefaultValue(@RequestParam(defaultValue = "guest") String userName,
                                          @RequestParam(defaultValue = "-99") Integer age) {

        log.info("userName = {}", userName);
        log.info("age = {}", age);

        return "OK";
    }

    /**
     *  Map 으로 모든 param 조회
     *  그냥 Map 으로 받는게 일반적이고, 필요한 경우 MultiValueMap 으로 받아주면 된다.
     */
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, String> map) {

        log.info("map = {}", map);
        
        // Map 은 forEach 지원 해 줌
        // 아래처럼 바로 key value 를 넘겨줘서 작업할 수 있음
        map.forEach( (key, value) -> log.info("key = {}, value = {}", key, value));
        // 그 외에는 아래처럼 출력할 수 있음
        // 1. entrySet() 으로 바로 forEach 돌리기 ( Map 에서 지원해주는 forEach 메서드의 BiConsumer 선언이 이 로직으로 되어 있음 )
        log.info("==== 방법 1 entrySet() 으로 바로 forEach 돌리기 ====");
        // 요 방법이 코드가 가장 깔끔하므로 잘 기억해 두자! Map.Entry<K, V> 에서 K, V 는 내가 Map 선언 할때 K ,V 와 당연히 같은 타입으로!
        for (Map.Entry<String, String> entry : map.entrySet()) {
            log.info("key = {}, value = {}", entry.getKey(), entry.getValue());
        }
        // 2. keySet 으로 key 값들 전부 Set으로 받아서 출력하기
        log.info("==== 방법 2 keySet 후 forEach문법 이용 ====");
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            log.info("key = {}, value = {}", key, map.get(key));
        }
        log.info("==== 방법 3 keySet후 stream에서 지원하는 forEach 문법 이용 ====");
        // 물론 Set 도 이렇게 stream 이용해서 출력 가능
        keySet.stream().forEach( key -> log.info("key = {}, value ={}", key, map.get(key)));

        return "OK";
    }

    /**
     *
     * @RequestBody 는 오로지 Http 메서드 요청에 대해서만 DTO 로 자동으로 매핑해주는 친구
     * @ModelAttribute 는 HTML 폼이나, 쿼리 파라미터, 헤더 값, PathVariable 에서 DTO 에 매핑되는 경우가 있으면 자동 매핑
     *
     * @ModelAttribute vs @RequestBody
     *
     * @ModelAttribute 는 path variables, 쿼리파라미터, request-헤더, HTML 폼에 대해서 DTO 랑 매핑
     * @RequestBody 는 HTTP 메서드 요청 바디에 있는 값들에 대해서 DTO 랑 매핑
     *
     */
    @RequestMapping("/model-dto-v1")
    public String dtoV1(@RequestBody HelloData helloData) {
        log.info("hello data ?? {}", helloData.getUsername());

        return "OK";
    }

    @RequestMapping("/model-attribute-v1")
    public String attributeV1(@RequestParam String username,
                              @RequestParam int age) {

        // 일반적인 @RequestParam 처리법 ( 쿼리파라미터, HTML 폼 전송 )
        log.info("username = {}", username);
        log.info("age={}", age);

        return "OK";
    }

    // JSON 으로 보내면 @ModelAttribute가 못읽는다. <= 이건 @RequestBody 로 읽어줘야 함
    // @ModelAttribute 가 읽을수 있는 정보 -> path variables , 쿼리 파라미터, HTML 폼, request-header 정보
    // 여기서 중요한건 데이터들이 흩어져있어도 DTO 에 해당하는 값들을 알아서 다 찾아준다
    // ex. 쿼리 파라미터에는 username 값, HTML 폼에는 age 값만 각각 전달해줘도 helloData 에 알아서 매핑된다.
    // 만약 key 값이 같은 여러 value 가 들어오면 String 인 경우 , 로 전부 붙여버리고
    // 나머지 타입들은 덮어써진다. ( String 인 경우 전부 , 로 이어져버린다는점 주의하자 이거 배열이아니라 하나에 String 이다  굳이 쓸거면 split 해야함)
    // String 아닌 경우에는 다음 우선순위로 하나만 할당 됨
    // HTML 폼 > 쿼리파라미터 > path variables > request-header
    @RequestMapping("/model-attribute-v2")
    public String attributeV2(@ModelAttribute("helData") HelloData helloData) {

        // 참고 @ModelAttribute("helData") HelloData helloData 이렇게 () 통해 명칭을 지정해주면
        // model.addAttribute("helData", helloData); 이 코드가 자동으로 세팅됨!!!

        log.info("HeloData = {}", helloData);
        log.info("username = {}", helloData.getUsername());
        log.info("age={}", helloData.getAge());

        return "OK";
    }
}
