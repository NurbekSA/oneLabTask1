package org.example.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    private String investmentType;  // Тип инвестиции (EQUITY, DEBT)
    private BigDecimal targetAmount;
    private BigDecimal actualAmount;
    private String currency;  // Валюта займа (USD, EUR)
    private OffsetDateTime dateOfOrder;  // Дата и время оформления займа
    private OffsetDateTime dueDate;  // Срок погашения займа
    private String status;  // Статус займа (PENDING, APPROVED, REJECTED)
    private String purpose; // цель
    private String description;
    private String collateral;  // Залог (если есть)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvestmentModel> investments;

    // Метод для добавления инвестиции
    public void addInvestment(InvestmentModel investment) {
        this.investments.add(investment);
        investment.setOrder(this);  // Устанавливаем связь с ордером
        // Обновляем фактическую сумму инвестиций
        this.actualAmount = this.actualAmount.add(investment.getAmount());
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDateOfOrder(OffsetDateTime dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public void setDueDate(OffsetDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCollateral(String collateral) {
        this.collateral = collateral;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public OffsetDateTime getDateOfOrder() {
        return dateOfOrder;
    }

    public OffsetDateTime getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getDescription() {
        return description;
    }

    public String getCollateral() {
        return collateral;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public List<InvestmentModel> getInvestments() {
        return investments;
    }

    public void setInvestments(List<InvestmentModel> investments) {
        this.investments = investments;
    }
}
