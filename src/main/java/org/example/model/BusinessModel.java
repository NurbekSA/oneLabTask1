package org.example.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;


@Entity
public class BusinessModel {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
