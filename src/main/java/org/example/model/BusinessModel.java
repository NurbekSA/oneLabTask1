package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BusinessModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderModel> orders;
    private String directorNumber;
    private String directorMail;
    private String isCheked;
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
}
