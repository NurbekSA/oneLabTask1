package org.example.repository;


import org.example.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends JpaRepository<CardModel, Long> {
    // Добавьте дополнительные методы поиска, если это необходимо
    CardModel findByCardNumber(String cardNumber);
}
