package org.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    @JsonIgnore
    private List<InvestmentModel> investments;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    @JsonIgnore
    private BusinessModel business;

    private Boolean isActive; //(инвестируемый одер собрал сумму/нет)
    private Boolean isAlive; // Логическое удаление

    private String investmentType;
    private BigDecimal targetAmount;
    private BigDecimal actualAmount;
    private String currency;
    private long dateOfOrder;
    private long dueDate;
    private String purpose;
    private String description;
    private String collateral;


}
