package org.example.service;

import org.example.model.CardModel;
import org.example.repository.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CardService {

    private final CardRepo cardRepo;

    @Autowired
    public CardService(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    // Получить все записи
    public List<CardModel> findAll() {
        return cardRepo.findAll();
    }

    // Получить запись по ID
    public CardModel findById(Long id) {
        return cardRepo.findById(id).orElse(null);
    }

    // Создать новую запись
    public CardModel create(CardModel cardModel) {
        cardModel.setCreatedAt(OffsetDateTime.now()); // Устанавливаем дату создания
        cardModel.setUpdatedAt(OffsetDateTime.now()); // Устанавливаем дату обновления
        return cardRepo.save(cardModel);
    }

    // Обновить существующую запись
    public CardModel update(Long id, CardModel updatedCardModel) {
        if (cardRepo.existsById(id)) {
            updatedCardModel.setId(id);
            updatedCardModel.setUpdatedAt(OffsetDateTime.now()); // Обновляем дату
            return cardRepo.save(updatedCardModel);
        }
        return null; // Или можно выбросить исключение
    }

    // Удалить запись
    public void delete(Long id) {
        cardRepo.deleteById(id);
    }
}
