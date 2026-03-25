package hello.aop.order.aop.exam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository repository;

    public void request(String itemId) {

        for (int i = 0; i < 5; i++) {
            repository.save(itemId + " / data" + i);
        }
    }

}
