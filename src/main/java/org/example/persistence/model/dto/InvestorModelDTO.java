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
public class InvestorModelDTO {

    private Long id;
    private List<Long> cardIds; // Список идентификаторов карт, вместо полной информации о CardModel
    private List<Long> investmentIds; // Список идентификаторов инвестиций, вместо полной информации о InvestmentModel

    private String mail;
    private String phoneNumber;
    private Boolean isChecked;
    private Double score;
    private String iin;
    private String fio;
    private String address;
    private String investorType;
    private Long createdAt;
    private Long updatedAt;
}

