package jpa.practice.web.repository;

import jakarta.persistence.EntityManager;
import jpa.practice.web.entity.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MemberRepository {

    private final EntityManager entityManager;

    public MemberRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

}
