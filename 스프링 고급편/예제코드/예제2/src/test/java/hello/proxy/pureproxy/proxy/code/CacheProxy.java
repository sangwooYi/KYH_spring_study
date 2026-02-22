package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject {

    // target 이 있어야함
    private Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");

        if (this.cacheValue == null) {
            // 캐싱!
            this.cacheValue = target.operation();
        }

        return cacheValue;
    }
}
