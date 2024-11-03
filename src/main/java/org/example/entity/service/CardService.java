package org.example.entity.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.model.exception.ResourceNotFoundException;
import org.example.entity.model.CardModel;
import org.example.entity.repository.CardRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepo cardRepo;

    public CardModel findById(Long id) {
        return cardRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card with id " + id + " not found"));
    }

    public CardModel create(CardModel cardModel) {
        if (cardModel == null) {
            throw new IllegalArgumentException("Card model cannot be null");
        }
        Long now = System.currentTimeMillis();
        cardModel.setCreatedAt(now);
        cardModel.setUpdatedAt(now);
        return cardRepo.save(cardModel);
    }
}
