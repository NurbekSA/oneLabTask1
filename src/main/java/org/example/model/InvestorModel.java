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
public class InvestorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String iin;
    private String fio;
    private String phoneNumber;
    private String mail;
    private String address;
    private String investorType; // Изменено на lowercase для соответствия стандартам Java
    private Long createdAt;
    private Long updatedAt;

    public String getMail() {
        return mail;
    }
}
