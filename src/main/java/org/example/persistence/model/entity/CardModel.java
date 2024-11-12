package org.example.persistence.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


import jakarta.persistence.*;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class CardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "investor_id", nullable = true)
    private InvestorModel investor;

    private String cardNumber;
    private String cardholderName;
    private String expiryDate;


    private long createdAt;
    private long updatedAt;
}