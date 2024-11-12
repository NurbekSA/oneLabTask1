package org.example.persistence.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderModelDTO {

    private Long id;
    private List<Long> investmentIds; // Идентификаторы инвестиций вместо полной информации о InvestmentModel
    private Long businessId; // Идентификатор бизнес-модели вместо полной информации о BusinessModel

    private Boolean isActive;
    private Boolean isAlive;
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
