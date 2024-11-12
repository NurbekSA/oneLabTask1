package org.example.persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardModelDTO {

    private Long id;
    private Long investorId; // Используем только идентификатор InvestorModel для уменьшения объема данных
    private String cardNumber;
    private String cardholderName;
    private String expiryDate;
    private long createdAt;
    private long updatedAt;
}