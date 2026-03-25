package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic{

    private ConcreteLogic realLogic;

    public TimeProxy(ConcreteLogic concreteLogic) {
        this.realLogic = concreteLogic;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");

        long startDatetime = System.currentTimeMillis();
        String result = realLogic.operation();
        long endDatetime = System.currentTimeMillis();
        long resultTime = endDatetime-startDatetime;
        log.info("소요 시간 = {}ms", resultTime);

        return result;
    }

}
