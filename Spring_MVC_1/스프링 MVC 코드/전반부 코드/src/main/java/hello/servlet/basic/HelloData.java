package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

// JSON 사용해서 HTTP API 메서드로 통신하려면
// 반드시 이렇게 DTO 정의해서 사용할 것
// lombok 사용하고있으면 @Getter  @Setter 만 해줘도 끝
@Getter
@Setter
public class HelloData {

    private String userName;
    private int age;

}
