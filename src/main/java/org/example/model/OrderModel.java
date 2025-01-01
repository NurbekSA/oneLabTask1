package org.example.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvestmentModel> investments;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private BusinessModel business;

    private Boolean isActive; //(инвестируемый одер собрал сумму/нет)
    private Boolean isAlive; // Логическое удаление

    private String investmentType;
    private BigDecimal targetAmount;
    private BigDecimal actualAmount;
    private String currency;
    private long dateOfOrder;
    private long dueDate;
    private String status;
    private String purpose;
    private String description;
    private String collateral;

    public OrderModel(Long id, List<InvestmentModel> investments, BusinessModel business, Boolean isActive,
                      Boolean isAlive, String investmentType, BigDecimal targetAmount,
                      BigDecimal actualAmount, String currency, long dateOfOrder,
                      long dueDate, String status, String purpose, String description,
                      String collateral, String aNull) {
        this.id = id;
        this.investments = investments;
        this.business = business;
        this.isActive = isActive;
        this.isAlive = isAlive;
        this.investmentType = investmentType;
        this.targetAmount = targetAmount;
        this.actualAmount = actualAmount;
        this.currency = currency;
        this.dateOfOrder = dateOfOrder;
        this.dueDate = dueDate;
        this.status = status;
        this.purpose = purpose;
        this.description = description;
        this.collateral = collateral;
    }
}
