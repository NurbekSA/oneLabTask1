package org.example.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Entity
public class InvestmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long investorId;
    private BigDecimal amount;  // Сумма инвестиции
    private OffsetDateTime investmentDate;
    private String status;  // Статус инвестиции (PENDING, COMPLETED)

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

    public void setId(Long id) {
        this.id = id;
    }

    public void setInvestmentDate(OffsetDateTime investmentDate){
        this.investmentDate = investmentDate;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
