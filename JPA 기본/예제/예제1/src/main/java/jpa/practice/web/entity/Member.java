package jpa.practice.web.entity;

import jakarta.persistence.*;
import jpa.practice.web.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
// Sequence 쓸거면 아래처럼 세팅 필요 ( 시퀀스는 Oracle 전용 )
//@SequenceGenerator(name = "seq_member_001", sequenceName = "member_seq", initialValue = 5, allocationSize = 50)
public class Member {

    @Id
    // Sequence 는 시퀀스에 위임 ( 주로 Oracle 에서 사용 Oracle 에 경우 Sequence 란 오브젝트가 별도로 존재 )
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member_001")
    // Identity 는 주로 MySql 에서 많이 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Identity 는 DB에 위임 ( 따라서 서버에서는 모름 )
    private Long id;

    private String name;

    private Integer age;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    // 근데 지금은 그냥 아래처럼 LocalDate, LocalDateTime 쓰면 @Temporal 필요 X
    private LocalDateTime testLocalDateTime;

    // ENUM 쓸거면 .STRING 이 필수! Ordinal 은 데이터 꼬일 수 있어 위험!
    // .ORDINAL 은 ENUM 순서 저장 ( 0, 1, 2.. ) STRING 은 ENUM 변수명으로 저장
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Lob
    private String description;

    // 하나의 팀에 여러 멤버가 속하는 N:1 구조 ! ( 팀이 1 )
    @ManyToOne
    // N 쪽에 @JoinColumn 을 적어주며, 여기서 지정해준 name 명칭이 실제 테이블의 FK(외래키) 컬럼명이다
    // 여기선 Member 테이블의 team_id 라는 컬럼에 매핑됨! ( 이부분 주의! )
    // 이렇게 외래키를 갖고 있는 쪽을 연관관계의 주인으로 해야 한다! 1:N 관계에서는 N 쪽이 주인
    // 연관관계의 주인의 경우에만 데이터 변경이 가능! // mappedBy 가 할당된 쪽에서는 READ ONLY !!
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    // 사실 Member 안에 Order 리스트가 포함되는건 비즈니스적으론 바람직 하지 않다.
    // 즉 우선 1:N 단방향 설정으로 설계 후
    // 양방향 설정 여부는 비즈니스적 측면으로 고려해서 추가로 설정하면 됨!
    // 단방향 -> 양방향으로 변경은 코드 몇줄만 추가하면 끝 + DB 에는 영향이 전혀 없기 때문 !
    @OneToMany(mappedBy = "member")
    List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        order.setMember(this);
        orders.add(order);
    }

    // 당연히 setter 로해서 setTeam 으로 해도 크게 문제는 없으나 아래처럼 명시적으로 하는게
    // 유지 보수 차원에서 더 바람직함! ( + 실제로는 1:N 관계에서 1쪽에다가 매핑용 메서드를 선언하는게 권장 됨 )
    // 혹시 양쪽에다가 매핑 메서드 선언해두면 자칫하면 무한루프에 빠져버릴 수 있다. 따라서 한쪽에만 선언하는게 바람직함.
//    public void changeTeam(Team team) {
//        this.team = team;
//        // 이렇게 메서드에 포함시키기!
//        team.getMembers().add(this);
//    }
}
