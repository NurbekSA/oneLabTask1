package org.example.persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentModelDTO {

    private Long id;
    private Long investorId; // Ссылаемся на ID инвестора вместо полной сущности
    private Long orderId; // Ссылаемся на ID заказа вместо полной сущности
    private Boolean isPaid;
    private Boolean isActive;
    private Boolean isAlive;
    private Double amount;
    private long investmentDate;
}
