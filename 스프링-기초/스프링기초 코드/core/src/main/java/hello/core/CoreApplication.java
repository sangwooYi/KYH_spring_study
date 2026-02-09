package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication 에 @ComponentScan이 이미 같이 걸려있다!
// 따라서 사실 @ComponentScan 어노테이션을 별도로 세팅할 이유가 없다! ( 별도로 범위를 좁혀야 하는경우 아닌이상!)
@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
	}
}
