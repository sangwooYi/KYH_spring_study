package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject subject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(subject);

        client.executed();
        client.executed();
        client.executed();
    }

    @Test
    void cacheProxyTest() {
        RealSubject subject = new RealSubject();
        // 클라이언트는 프록시에 요청하는 구조로 바뀜
        // 프록시가 실제 서버를 참조하므로 client -> proxy -> server 해당 흐름을 갖게 됨
        CacheProxy proxy = new CacheProxy(subject);
        ProxyPatternClient client = new ProxyPatternClient(proxy);

        client.executed();
        client.executed();
        client.executed();
    }

}
