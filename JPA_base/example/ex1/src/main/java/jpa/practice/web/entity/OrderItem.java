package jpa.practice.web.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.aspectj.weaver.ast.Or;

// N:M 관계는 이렇게 외래키 매핑용 중간테이블을 통해 1:N / M:1 단방향 관계로 설정하는게 바람직!
@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

}
