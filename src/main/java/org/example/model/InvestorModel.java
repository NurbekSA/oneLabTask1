package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;

@Entity
public class InvestorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String iin;
    private String fio;
    private String phoneNumber;
    private String mail;
    private String address;
    private String investorType; // Изменено на lowercase для соответствия стандартам Java
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInvestorType(String investorType) {
        this.investorType = investorType;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getIin() {
        return iin;
    }

    public String getFio() {
        return fio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public String getAddress() {
        return address;
    }

    public String getInvestorType() {
        return investorType;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
