package org.example.persistence.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvestmentModel> investments;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = true)
    private BusinessModel business;

    private Boolean isActive; //(инвестируемый одер собрал сумму/нет)
    private Boolean isAlive; // Логическое удаление

    private String investmentType;
    private Double targetAmount;
    private Double actualAmount;
    private String currency;
    private String purpose;
    private String description;
    private String collateral;

    private long dateOfOrder;
    private long dueDate;

}
