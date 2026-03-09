package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    // 메서드 실행 전에 각각 호출
    @BeforeEach
    public void init() throws NoSuchMethodException {
        // getMethod(메서드명, 반환 타입)
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        // 여기서 나오는 값이 AspectJ 표현식에서 execution 에 매칭되는 부분임!
        log.info("helloMethod = {}", helloMethod);
    }

    // 그냥 명확히 경로 찍어주는 Ver
    @Test
    void exactMatch() {
        pointcut.setExpression("execution(public String hello.aop.order.aop.member.MemberServiceImpl.hello(String))");
        // matches(메서드, 대상클래스) 형태로 실행하며 지정한 대상클래스의 메서드가 현재 포인트컷에 해당하는지를 체크해 줌
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 그냥 모든걸 생략한 Ver
    // 접근제어자 (public , protected, private) , 선언타입은 아예 생략 가능!
    @Test
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void namePatternMatch() {
        // 패턴적용도 됨
        pointcut.setExpression("execution(* hell*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    void namePatternMatch2() {
        // 패턴적용도 됨
        pointcut.setExpression("execution(* *el*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 주의 한 메서드에 포인트컷을 여러개 세팅하면 덮어써지는것이 아니라 같이 등록된다! (여러 포인트컷이 등록되는 것)
    // matches() 메서드는 그 포인트컷중 하나라도 매칭되면 True가 되어버린다
    @Test
    void nameMatchFalse() {
        // 패턴적용도 됨
        pointcut.setExpression("execution(* nono(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch() {
        // 패턴적용도 됨
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // member 패키지에 속한 모든 메서드
    // .* 만 할 때는 정말 지정한 경로의 패키지에 바로 직접 속해있는 클래스여야만 한다.
    // ..* -> 지정한 클래스 하위에 모든 경로까지 전부 포함하고싶을때는 ..*  로 해주어야 함!
    // 즉 .는 정확히 지정한 경로, ..는 하위 sub 패키지까지 전부 포함하겠다는 의미
    @Test
    void packageExactMatch1() {
        // 패턴적용도 됨
        pointcut.setExpression("execution(* hello.aop.order.aop.member.*.hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalseMatch1() {
        // 지정 패키지경로에 직접 속한 클래스만 OK로 됨 주의! ( 하위 모든경로를 체크하고싶으면 ..* 로 !)
        pointcut.setExpression("execution(* hello.aop.order.aop.*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // 패키지경로..* 형태로 찍어주면 지정한 패키지 경로 하위에 속한 모든 파일을 체크함!
    // hello.aop.order.aop 패키지 하위에 모든 파일에 있는 hello 메서드
    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* hello.aop.order.aop..*.hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    
    // hello.aop.order.aop 패키지 하위에 모든 파일에 있는 he 로 시작하는 메서드
    @Test
    void packagePatternMatch1() {
        pointcut.setExpression("execution(* hello.aop.order.aop..*.he*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // hello.aop.order 패키지 하위에 모든 파일에 있는 모든 메서드
    @Test
    void packagePatternMatch2() {
        pointcut.setExpression("execution(* hello.aop.order..*.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeExactMatch() {
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // execution 에 지정한 타입정보가 (클래스, 인터페이스 등) 부모 타입이어도 매칭 된다.
    // -> 당연히 부모 타입에도 있는 메서드인 경우에만!!
    // -> 당연히 반대의 경우 ( execution에 지정한 타입이 자식인 경우 ) 는 안 된다.
    @Test
    void typeSuperTypeMatch() {
        // 패턴적용도 됨
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberService.*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    // 당연하지만 부모타입에는 없는 메서드를 체크하라고하면 False 이다.
    //
    @Test
    void typeMatchInternal1() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void typeMatchInternal2() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }


    // String 타입의 args 허용
    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    
    // Parameter 없는 경우만
    // (..) 는 모든 Parameter 없던, 있던 상관 X , () 는 Parameter가 없어야만 True,
    // (*) 는 Parameter 하나만, 대신 모든 타입 허용, (*, *) 는 Parameter 2개 와야함, 대신 모든 타입 허용 이런 식!
    @Test
    void argsMatchFalse() {
        pointcut.setExpression("execution(* *())");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    // String 타입이 처음 와야하고, 그 이후는 무관
    // ( 이렇게 제약조건을 디테일하게 설정 가능! )
    @Test
    void argsMatch2() {
        pointcut.setExpression("execution(* *(String, ..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
