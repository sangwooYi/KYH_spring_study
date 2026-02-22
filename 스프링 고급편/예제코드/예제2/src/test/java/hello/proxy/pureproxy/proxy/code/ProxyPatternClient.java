package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

// 테스트용 클라이언트
@Slf4j
public class ProxyPatternClient {

    private Subject subject;

    public ProxyPatternClient(Subject subject) {
        this.subject = subject;
    }

    public void executed() {
        String res = subject.operation();
        log.info("res = {}", res);
    }
}
