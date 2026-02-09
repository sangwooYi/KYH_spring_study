package hello.core.beanfind;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBeanFindSameName {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시에 중복이 있으면 오류 발생")
    void findBeanWhenDuplicate() {

        // 이렇게 타입으로만 조회시에, 동일한 타입으로 2개 이상 나오면 그냥
        // NoUniqueBeanDefinitionException  예외발생!
        //MemberRepository memberRepository = ac.getBean(MemberRepository.class);
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(MemberRepository.class));

    }

    @Test
    @DisplayName("타입으로 조회시에 중복이 있으면 반드시 이름까지 포함해서 조회")
    void findBeanByName() {

        // 이렇게 타입으로만 조회시에, 동일한 타입으로 2개 이상 나오면 그냥
        // NoUniqueBeanDefinitionException  예외발생!
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);

        Assertions.assertThat(memberRepository).isInstanceOf(MemoryMemberRepository.class);

    }

    @Test
    @DisplayName("타입으로 조회시에 중복이 있으면 반드시 이름까지 포함해서 조회")
    void findAllBeanByDuplicateType() {

        // 이렇게 타입으로만 조회시에, 동일한 타입으로 2개 이상 나오면 그냥
        // NoUniqueBeanDefinitionException  예외발생!
        // Object로 조회시 모든 빈을 그냥 다 가져온다
        //Map<String,Object> memberRepositoryMap = ac.getBeansOfType(Object.class);
        Map<String, MemberRepository> memberRepositoryMap = ac.getBeansOfType(MemberRepository.class);
        for (String key : memberRepositoryMap.keySet()) {
            System.out.printf("key = %s value = %s\n", key, memberRepositoryMap.get(key));
        }
        //Assertions.assertThat(memberRepositoryMap.size()).isEqualTo(2);
    }

    @Configuration
    public static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }
        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
