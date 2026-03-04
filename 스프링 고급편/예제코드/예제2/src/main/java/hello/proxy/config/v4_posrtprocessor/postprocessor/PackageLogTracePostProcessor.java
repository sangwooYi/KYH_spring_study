package hello.proxy.config.v4_posrtprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {

    private final String basePackage;
    private final Advisor advisor;

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    // 빈을 프록시로 등록하던 작업을 이 메서드를 통해 자동화가 가능해 졌다!
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("beanName = {}, bean.getClass() = {}, package = {}", beanName, bean.getClass(), bean.getClass().getPackage());

        // 프록시 적용 대상
        // 대상 아니면 원본 그대로, 대상이면 프록시로 교체
        String packageName = bean.getClass().getPackageName();     // 빈의 패키지 정보 가져오기

        if (!packageName.startsWith(basePackage)) {
            return bean;
        }

        // 프록시를 만들어 반환 traget 은 bean
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        Object proxy = proxyFactory.getProxy();
        log.info("bean class = {} / proxy class = {}",bean.getClass(),  proxy.getClass());

        return proxy;
    }
}
