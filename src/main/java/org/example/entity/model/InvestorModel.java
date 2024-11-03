package org.example.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class InvestorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CardModel> cards;
    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<InvestmentModel> investment;


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
