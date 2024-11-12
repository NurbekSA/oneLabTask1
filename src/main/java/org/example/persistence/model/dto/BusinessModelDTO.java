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
public class BusinessModelDTO {

    private Long id;
    private String directorNumber;
    private String directorMail;
    private Boolean isCheked;
    private String directorFIO;
    private String directorIIN;
    private String companyName;
    private String companyBIN;
    private String address;
    private String typeOfPaymentSystem;
    private String sector;
    private Long dateOfBusinessStarted;
    private Long createdAt;
    private Long updatedAt;

    // Чтобы включить только нужную информацию из OrderModel, можно добавить список ID заказов.
    private List<Long> orderIds;
}