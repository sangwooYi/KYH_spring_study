package jpa.practice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpa.practice.web.RoleType;
import jpa.practice.web.entity.Member;
import jpa.practice.web.entity.Team;
import jpa.practice.web.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
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

                Team team = new Team();
                team.setName("TEAM A");
                em.persist(team);
                Member member = new Member();
                member.setName("HAHA");
                //member.changeTeam(team);    // 양방향 매핑 메서드 적용
                member.setRoleType(RoleType.Member);
                em.persist(member);

                // 양방향 매핑메서드 적용 ( 1:N 일때 1 쪽에다가만 선언해두는게 바람직! )
                team.addMember(member);

                // 이 부분은 사실 실제로 별도로 쿼리로는 실행은 안되나, 그냥 같이 적어주는걸 습관화하자!
                // 이 부분을 안 적는 경우 같은 트랜잭션에서 team 을 다시 조회하는 경우 문제가 발생 함! ( 1차 캐쉬에서 조회하므로 )
                // ( Member 를 명시적으로 add 함으로써 영속성 컨텍스트에서 TEAM 쪽도 변경으로 인지되도록 해주어야 한다! )
                // team.getMembers().add(member);  ( 이 부분은 메서드를 통해 자동화 하는걸 권장함! <= 자동화!)

                // AUTO_GENERATE 인 경우 사실 저장 시점에 서버가 PK 값을 알 수 없으나 ( DB나 시퀀스 위임이므로)
                // JPA 의 경우 영속성 컨텍스트에 할당 되면 아래처럼 PK 값을 서버측에서 알 수 있다!
                // 이건 JPA 영속성 컨텍스트에서 영속성을 관리해주며 제공해주는 기능!
                log.info("memberId = {}", member.getId());
                return null; // void 반환 시 null
            });

            System.out.println("저장 완료");
        };
    }
}
