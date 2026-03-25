package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

// 참고 @Controller 와 @RestController 의 차이!
// @RestController 는 @Controller 에 @ResponseBody 어노테이션 설정이 추가 된 어노테이션
// 그래서 return 값을 단순히 String 으로만 보내줘도 찰떡같이 자바 객체를 HTTP 응답 본문의 객체로 변환해줌
// 참고 @RequestBody 는 HTTP 요청 바디의 정보를 자바 객체로 매핑해서 변환해주는 역할
// @ResponseBody 는 자바 객체를 HTTP 응답 바디의 본문 객체로 변환해 주는 역할  ( 둘다 일반적으로 JSON 형태로 ! )
// 참고로 HTML Form 형태로 전달해주는건 RequestParam 으로 받고,
// JSON 형태로 (HTTP 메서드) 로 전달준건 @RequestBody로 받는다
@RequestMapping("/mapping/users")
@RestController
public class MappingClassController {

    // 아래처럼 단순하게 반환해버리면 @Controller 로만 설정된 경우에는 500 에러 발생한다!
    // 그냥 @Controller 를 쓰는 경우에는 이렇게 단순 반환이 안되고 자바 객체 -> JSON 객체로 변환해서
    // response 에 담아주는 작업이 필요함!
    @GetMapping("/test-test")
    public String test() {
        return "OK";
    }

    // 실제로 REST API 목적을 살려 URL 설계를 아래 형태로 하는걸 기본으로!
    @GetMapping("")
    public String user() {
        return "get users";
    }

    @PostMapping("")
    public String addUser() {
        return "post user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable("userId") String userId) {
        return "find user = " + userId;
    }

    @PutMapping("/{userId}")
    public String putUser(@PathVariable("userId") String userId) {
        return "Put user = " + userId;
    }

    @PatchMapping("/{userId}")
    public String patchUser(@PathVariable("userId") String userId) {
        return "Patch user = " + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        return "Delete User = " + userId;
    }
}
