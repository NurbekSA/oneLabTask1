package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String companyName;
    private String companyBIN;
    private String address;
    private String typeOfPaymentSystem;
    private String sector;
    private Long dateOfBusinessStarted;
    private String directorFIO;
    private String directorIIN;
    private String directorNumber;
    private String directorMail;
    private Long createdAt;
    private Long updatedAt;



}
