package hello.core;

// 애초에 테스트 폴더에만든 파일은 다른 파일에서 끌어올수 없도록 제한하고 있음
public class SingletonService {

    private static SingletonService instance = new SingletonService();

    private SingletonService() {

    }

    public static SingletonService getInstance() {
        return instance;
    }
    
}

