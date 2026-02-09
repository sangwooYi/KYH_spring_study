package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {

    /**
     * 응답 보내는 가장 고전적인 방법
     * response 의 Writer 활용
     * 
     * @param response
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("OK");
    }


    /**
     * ResponseEntity 사용
     * 이게 그나마 낫다
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() throws IOException {
       return new ResponseEntity<>("OKOK", HttpStatus.OK);
    }

    /**
     * 그냥
     * @ResponseBody 사용 하면 HTTP 바디에 데이터를 그대로 넣어준다
     * @return
     * @throws IOException
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3()  {
        return "OKOK";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseJsonValueV1() {

        HelloData helloData = new HelloData();

        helloData.setUsername("이상우");
        helloData.setAge(34);

        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    /**
     * @ResponseBody 사용시 이렇게 Object 도 바로 반환해서 응답 바디에 넣어 보내줄 수 있다.
     * 근데 이렇게 바로 Object를 반환할 떄 HTTP Status 값을 우리가 지정할 수 없으므로
     * 필요하면 @ResponseStatus 를 사용  or 그냥 ResponseEntity 사용 중에 선택
     * 여기서 포인트는 만약 동적으로 HttpStatus 를 응답해야하는 로직이면 ResponseEntity 를 써야함!
     *
     * @return
     */

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseJsonValueV2() {
        HelloData helloData = new HelloData();

        helloData.setUsername("이상우");
        helloData.setAge(34);

        return helloData;
    }
}
