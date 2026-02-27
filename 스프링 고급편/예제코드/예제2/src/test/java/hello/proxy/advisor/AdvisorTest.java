package hello.proxy.advisor;

import hello.proxy.common.ServiceImpl;
import hello.proxy.common.ServiceInterface;
import hello.proxy.common.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTest {

    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // PointCut, Advice 정보 넘겨주면된다.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // PointCut, Advice 정보 넘겨주면된다.
        // PointCut 은 getClassFilter && getMethodMatcher 둘다 참으로 반환되는 경우만
        // Advice 를 호출해 주는 필터 역할!, 하나라도 false 즉 필터링 되는 경우라면
        // Advice 없이 바로 target을 호출 해 준다.
        // 즉 PointCut 결과 True -> Advice 호출 -> Advice가 target 호출
        //    PointCut 결과 False -> 바로 target 호출
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice());

        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class MyPointCut implements Pointcut {
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher {

        private String MATCH_NAME = "save";

        // 정적 정보로 결정 되는 경우 호출
        @Override
        public boolean matches(Method method, Class<?> targetClass) {

            boolean isMatch = method.getName().equals(MATCH_NAME);

            log.info("포인트컷 호출 method = {}, targetClass = {}", method.getClass(), targetClass);
            log.info("isMatch = {}", isMatch);

            return isMatch;
        }

        /*
            아래 두 메서드는 거의 쓸 일 없다.
            isRuntime 이 true 인 경우면
            아래 args 가 인수로 들어간 matches 가 호출 된다. ( 말그대로 정보를 동적으로 부여,
            Runtime 때 매개변수가 결정 되는 경우 사용 것 )
         */

        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }

    @Test
    @DisplayName("스프링 제공 포인트컷 사용")
    void advisorTest3() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 말그대로 Name 을 통한 Method 포인트컷
        // 내부적으로 PatternMatchUtils.simpleMatch(mappedNamePattern, methodName) 이걸 사용한다.
        // 따라서 *메서드명* 이런식으로 pattern 형태로 지정 가능!
        // PointCut 종류는 여러가지이며 실무에선 AspectJExpressionPointcut 이라는 걸 가장 많이 씀
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("save");


        // PointCut, Advice 정보 넘겨주면된다.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    // 변수로 클래스 메타정보를 받으려면 Class<?> 타입으로 선언해 주어야 함! 기억하자.
    private void printProxyTargetInfo(Object target, Object proxy, Class<?> targetClass) {

        if (targetClass.isInstance(proxy) && targetClass.isInstance(target)) {

            log.info("targetClass = {}", target.getClass());
            log.info("proxyClass = {}", proxy.getClass());

            // JDK 동적 프록시 여부
            log.info("AopUtils.isJdkDynamicProxy(proxy) = {}", AopUtils.isJdkDynamicProxy(proxy));
            // CGLIB 프록시 여부
            log.info("AopUtils.isCglibProxy(proxy) = {}", AopUtils.isCglibProxy(proxy));
            // 이건 스프링 동적 프록시 사용했으면 항상 참
            log.info("AopUtils.isAopProxy(proxy) = {}", AopUtils.isAopProxy(proxy));
        }
    }
}
