package org.example.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;
import java.rmi.server.UID;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String cardNumber;
    private String cardholderName;
    private String expiryDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
