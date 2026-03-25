package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


// 빈 라이프 사이클 관련
// 객체 생성 -> 의존관계 주입 -> init 메서드 -> 사용 -> destroy 메서드 -> 빈 해제

// 빈 사이클 관련 조작 법

// 1. InitializingBean, DisposableBean 상속 받아 오버라이딩 (스프링 의존성이 너무 강해 잘 안쓴다. )
// public class NetworkClient implements InitializingBean, DisposableBean

// 2. @Bean(initMethod = "init", destroyMethod = "close") 이렇게 빈에다가 지정
// 메서드명 자유롭게 설정가능 ( init 메서드명은 init, destroy 메서드는 close로 내가 정한것, "" 에 적기 나름

// 3. 애노테이션 사용 션 @PostConstruct, @PreDestroy 그냥 이거 쓰자
// PostConstruct 가 init 메서드 ( 말그대로 Construct 이후 (주입관계까지 완료 이후
// / PreDestroy 가 close 메서드 역할 ( 말 그대로 Destroy 직전 호출 )
// 요 방법이 가장 좋으나, 유일한 단점은 외부라이브러리에는 적용 불가능, 따라서 외부라이브러리때
// 빈사이클 관련해서 조작하고 싶은 경우에는 2번 방법을 사용하자! (1번은 굳이..)

public class NetworkClient {

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출 url ＝　" + this.url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect !! url = " + this.url);
    }

    public void call(String message) {
        System.out.println("call url = " + this.url + "/ message = " + message);
    }

    // 서비스 종료시 호출
    public void disConnect() {
        System.out.println("close !! url = " + this.url);
    }

    @PostConstruct
    public void init() {
        System.out.println("Network init");
        connect();
        call("초기화 연결");
    }

    @PreDestroy
    public void close() {
        System.out.println("Network Close");
        disConnect();
    }
    // InitializingBean 에 존재하는 메서드, 빈 초기화 용
    // 빈 생성 후, 의존관계 주입 완료된 이후 이 메서드가 호출 됨
    // 잘 안쓰는방법이긴 함
    /*
    @Override
    public void afterPropertiesSet() throws Exception {
        this.connect();
        this.call("초기화 진행이요");
    }

    // DisposableBean 에 존재하는 메서드 ( 빈 종료 용 )
    // 이 메서드 호출 된 이후 빈 컨테이너 종료 됨
    @Override
    public void destroy() throws Exception {
        this.disConnect();
    }*/

}
