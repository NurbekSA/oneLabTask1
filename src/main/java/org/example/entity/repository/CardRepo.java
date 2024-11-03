package org.example.entity.repository;


import org.example.entity.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends JpaRepository<CardModel, Long> {
    CardModel findByCardNumber(String cardNumber);
}
