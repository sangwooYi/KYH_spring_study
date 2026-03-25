package jpa.practice.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // 1:N 관계에서 1인 경우에는 아래처럼 리스트로 N 쪽을 저장!
    // ( mappedBy 에 매핑되는 값은 상대 엔티티에 선언된 TEAM 엔티티 참조 필드명!!! 이부분 주의!! )
    // 1 : N 관계에서!
    // @OneToMany 에는 mappedBy ( 여기에 대응되는 값은 상대 엔티티에서 이 엔티티를 참조하는 필드명! )
    // @ManyToOne 에는 @JoinColumn(name = "team_Id") 을 통해 FK 컬럼명과 매핑!!]
    // mappedBy 는 연관관계 주인에게는 절대 할당하면 안됨! 즉 1:N 관계에서는 N 쪽이 연관관계의 주인인 것!
    // mappedBy 설정된 쪽은 연관관계에서 READ ONLY 임! 이 부분 주의!!!
    @OneToMany(mappedBy = "team")
    List<Member> members = new ArrayList<>();

    // 이렇게 1 쪽에다가 추가 메서드 만드는게 더 권장 됨!
    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }
}
