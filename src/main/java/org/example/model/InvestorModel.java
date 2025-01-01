package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class InvestorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CardModel> cards;
    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvestmentModel> investment;

    private Boolean isChecked;

    private BigDecimal score;

    private String iin;
    private String fio;
    private String phoneNumber;
    private String mail;
    private String address;
    private String investorType;
    private Long createdAt;
    private Long updatedAt;



}
