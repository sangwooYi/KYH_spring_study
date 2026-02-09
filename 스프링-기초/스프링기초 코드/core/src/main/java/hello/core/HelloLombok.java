package hello.core;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
//@NoArgsConstructor    // @NoArgsConstructor 는 생성자 없는 디폴트 Constructor을 자동으로 만들어주는 어노테이션
//@RequiredArgsConstructor // @RequiredArgsConstructor final 이 붙은 필드를 매개변수로 하는 생성자를 자동으로 생성해 줌
//@AllArgsConstructor   // @AllArgsConstructor 는 생성자에 모든 필드를 매개변수로 받는 생성자를 자동으로 만들어주는 어노테이션
// 위의 생성자 어노테이션 쓰는데 수기로 생성자 만들면 오히려 에러남
// 따라서 최신에는 lombok 이용해서 필요한 필드만 private final 로 선언해두고
// @Getter 와 @RequiredArgsConstructor 를 명시하는 방식을 가장 많이 씀!
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();

        // @Getter / @Setter 어노테이션만 명시하면
        // getter setter 를 롬복이 알아서 세팅해준다..!
        // lombok 에서 자주 쓰이는게 @Getter, @Setter, @NoArgsConstructor, 그리고 필요시 @AllArgsConstructor, @ToString 자주씀
        helloLombok.setName("안녕");
        helloLombok.setAge(24);

        System.out.println("이름 : " + helloLombok.getName());
        System.out.println(helloLombok);

    }

}
