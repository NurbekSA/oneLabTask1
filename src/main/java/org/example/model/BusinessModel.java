package org.example.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;


@Entity
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String companyBIN;
    private String address;
    private String typeOfPaymentSystem;
    private String sector;
    private OffsetDateTime dateOfBusinessStarted;
    private String directorFIO;
    private String directorIIN;
    private String directorNumber;
    private String directorMail;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
