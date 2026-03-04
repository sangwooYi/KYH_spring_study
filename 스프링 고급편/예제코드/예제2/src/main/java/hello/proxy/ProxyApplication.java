package hello.proxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v1_proxy.ConcreteProxyConfig;
import hello.proxy.config.v1_proxy.InterfaceProxyConfig;
import hello.proxy.config.v2_dyamicproxy.DynamicProxyBasicConfig;
import hello.proxy.config.v2_dyamicproxy.DynamicProxyFilterConfig;
import hello.proxy.config.v3_proxyfactory.ProxyFactoryConfigV1;
import hello.proxy.config.v3_proxyfactory.ProxyFactoryConfigV2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @Import 역할 : 
 * Config 파일을 어떤거로 할지 설정해주는 어노테이션
 * @Configuration 설정된 컨피그 파일이 여러개인 경우 사용하는 어노테이션
 * 
 * scanBasePackages 역할 :
 * 스캔 범위 수동 설정 ( 설정 별도로 없으면 그냥 @SpringBootApplication 하위에 모든 파일이 스캔 대상이 됨 )
 * 정확히 말하면 @ComponentScan 이 스캔 대상이 위와 같은 기준으로 정해지며, @SpringBootApplication 에는
 * @ComponentScan 어노테이션이 포함되어있다!!
 */

@Import(ProxyFactoryConfigV2.class)
//@Import({ProxyFactoryConfigV1.class, AppV2Config.class})
//@Import({DynamicProxyFilterConfig.class, AppV2Config.class})
//@Import({DynamicProxyBasicConfig.class, AppV2Config.class})
//@Import(DynamicProxyBasicConfig.class)
//@Import(ConcreteProxyConfig.class)
//@Import({AppV1Config.class, AppV2Config.class, InterfaceProxyConfig.class})
@SpringBootApplication(scanBasePackages="hello.proxy.app")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
