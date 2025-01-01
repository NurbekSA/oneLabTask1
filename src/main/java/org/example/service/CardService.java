package org.example.service;

import org.example.model.CardModel;
import org.example.repository.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    Long now = System.currentTimeMillis();

    private final CardRepo cardRepo;

    @Autowired
    public CardService(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    public ResponseEntity<CardModel> findById(Long id) {
        CardModel cardModel = cardRepo.findById(id).orElse(null);
        if (cardModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(cardModel);
    }

    public ResponseEntity<?> create(CardModel cardModel) {
        cardModel.setCreatedAt(now); // Устанавливаем дату создания
        cardModel.setUpdatedAt(now); // Устанавливаем дату обновления
        CardModel savedCard = cardRepo.save(cardModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }
}
