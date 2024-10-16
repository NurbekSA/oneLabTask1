package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;
import java.math.BigDecimal;

@Entity
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyId;
    private BigDecimal targetAmount;
    private BigDecimal actualAmount;
    private String currency;  // Валюта займа (USD, EUR)
    private OffsetDateTime dateOfOrder;  // Дата и время оформления займа
    private OffsetDateTime dueDate;  // Срок погашения займа
    private String status;  // Статус займа (PENDING, APPROVED, REJECTED)
    private String purpose; // цель
    private String description;
    private String collateral;  // Залог (если есть)


}
