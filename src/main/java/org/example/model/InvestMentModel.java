package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Entity
public class InvestMent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String investorId;
    private String orderId;
    private BigDecimal amount;  // Сумма инвестиции
    private OffsetDateTime investmentDate;
    private String investmentType;  // Тип инвестиции (EQUITY, DEBT)
    private String status;  // Статус инвестиции (PENDING, COMPLETED)
}
