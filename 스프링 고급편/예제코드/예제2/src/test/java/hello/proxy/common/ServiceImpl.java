package hello.proxy.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceImpl implements ServiceInterface{
    @Override
    public void find() {
        log.info("ServiceImpl.find 호출");
    }

    @Override
    public void save() {
        log.info("ServiceImpl.save 호출");
    }
}
