package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;


@Entity
public class Investor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iin;
    private String fio;
    private String phoneNumber;
    private String mail;
    private String address;
    private String InvestorType;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
