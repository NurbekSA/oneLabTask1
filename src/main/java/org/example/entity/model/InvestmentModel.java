package org.example.entity.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvestmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "investor_id", nullable = false)
    private InvestorModel investor;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderModel order;
    private Boolean isPaid; // (оплачено/не оплачено)
    private Boolean isActive; //(инвестируемый одер собрал сумму/нет)
    private Boolean isAlive; // Логическое удаление

    private Double amount;
    private long investmentDate;

}
