package org.example.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "investor_id", nullable = false)
    private InvestorModel investor;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvestmentModel> investments;

    // Конструктор, геттеры и сеттеры

}
