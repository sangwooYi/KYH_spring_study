package jpa.practice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa.practice.web.entity.Member;
import jpa.practice.web.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Import(JpaConfig.class)
@SpringBootApplication(scanBasePackages = "jpa.practice.web")
public class PracticeApplication {

	public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
	}

    /**
     * Main 에서 바로 실행하고 싶으면 아래처럼 해주어야 함 ( 참고만 )
     * CommandLineRunner 사용하여 Bean 으로 등록
     */
    @Bean
    public CommandLineRunner testSave(EntityManager em, TransactionTemplate transactionTemplate) {
        return args -> {
            // transactionTemplate을 사용하여 트랜잭션 범위를 지정합니다.
            transactionTemplate.execute(status -> {
                Member member = em.find(Member.class, 1L);
                member.setName("HAHA");


                return null; // void 반환 시 null
            });

            System.out.println("저장 완료");
        };
    }
}
