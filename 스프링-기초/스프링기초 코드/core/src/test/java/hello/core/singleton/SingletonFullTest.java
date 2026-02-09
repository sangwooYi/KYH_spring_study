package hello.core.singleton;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class SingletonFullTest {

    //private int price;
    /*
    public void order(String name, int price) {
        System.out.printf("%s 음식이 %d 원\n", name, price);
        this.price = price;
    }*/

    // 이런식으로 클라이언트가 필드에 영향을 못주도록 만드는게
    // 무상태 (stateless) 로 설계하는 것!
    // 무상태 조건이
    // 1. 클라이언트에 의존적인 핊드가 있으면 안됨
    // 2. 클라이언트가 변경할 수 있는 필드가 있으면 안 됨
    // 3. 가급적 Readonly 여야만 한다.
    // 4. 필드대신 로컬변수, parameter, TreadLocal 등을 사용하여 변수를 다룰 것!
    public int order(String name, int price) {
        System.out.printf("%s 음식이 %d 원\n", name, price);
        return price;
    }

    /*
    public int getPrice() {
        return this.price;
    }*/

    /*
    private static final SingletonFullTest singleTonInstance = new SingletonFullTest();
    private int price;

    private SingletonFullTest() {
    }

    private void order(String name, int price) {
        System.out.printf("%s 음식이 %d 원\n", name, price);
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }

    public static SingletonFullTest getInstance() {
        return singleTonInstance;
    }

    public static void main(String[] args) {

        SingletonFullTest instance1 = SingletonFullTest.getInstance();
        SingletonFullTest instance2 = SingletonFullTest.getInstance();

        instance1.order("김치", 10000);
        instance2.order("피자", 2000000);

        System.out.println(instance1.getPrice());
    }
    */
}
