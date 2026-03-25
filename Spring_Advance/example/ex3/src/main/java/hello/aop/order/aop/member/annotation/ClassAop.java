package hello.aop.order.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) // @Retention 언제까지 살아있는지를 설정 
// RUNTIME 은 프로그램 시작 이후에도 계속 남아있는걸 의미 ( 남아 있다는건 이 인터페이스 파일을 참조 가능하다는 것 )
// SOURCE 는 컴파일 할 때 (.class 만들 때) , CLASS 는 JVM 메모리에 올라갈 때 ( 클래스 로딩 될 때 ) 사라지는 옵션이며
// 사라진다는 건 이 인터페이스에 대해 정보 읽어오는게 불가능하다는 것 ( 역할만 수행하고 메모리에는 안 올라 가는 것 ! )
public @interface ClassAop {

}
