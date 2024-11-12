package org.example.persistence.repository;


import org.example.persistence.model.entity.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends JpaRepository<CardModel, Long> {
    CardModel findByCardNumber(String cardNumber);
}
