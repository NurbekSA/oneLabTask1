package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


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

    private BigDecimal amount;
    private long investmentDate;


}
