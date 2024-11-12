package org.example.persistence.service;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.dto.CardModelDTO;
import org.example.persistence.model.entity.CardModel;
import org.example.persistence.model.exception.ResourceNotFoundException;
import org.example.persistence.repository.CardRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepo cardRepo;

    // Преобразование из CardModel в CardModelDTO
    private CardModelDTO convertToDto(CardModel cardModel) {
        return new CardModelDTO(
                cardModel.getId(),
                cardModel.getInvestor() != null ? cardModel.getInvestor().getId() : null,
                cardModel.getCardNumber(),
                cardModel.getCardholderName(),
                cardModel.getExpiryDate(),
                cardModel.getCreatedAt(),
                cardModel.getUpdatedAt()
        );
    }

    // Преобразование из CardModelDTO в CardModel
    private CardModel convertToEntity(CardModelDTO cardDTO) {
        CardModel cardModel = new CardModel();
        cardModel.setId(cardDTO.getId());
        cardModel.setCardNumber(cardDTO.getCardNumber());
        cardModel.setCardholderName(cardDTO.getCardholderName());
        cardModel.setExpiryDate(cardDTO.getExpiryDate());
        cardModel.setCreatedAt(cardDTO.getCreatedAt());
        cardModel.setUpdatedAt(cardDTO.getUpdatedAt());
        return cardModel;
    }

    public CardModelDTO findById(Long id) {
        CardModel cardModel = cardRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card with id " + id + " not found"));
        return convertToDto(cardModel);
    }

    public CardModelDTO create(CardModelDTO cardDTO) {
        if (cardDTO == null) {
            throw new IllegalArgumentException("Card DTO cannot be null");
        }
        CardModel cardModel = convertToEntity(cardDTO);
        Long now = System.currentTimeMillis();
        cardModel.setCreatedAt(now);
        cardModel.setUpdatedAt(now);
        CardModel savedCard = cardRepo.save(cardModel);
        return convertToDto(savedCard);
    }

    public void delete(Long id) {
        if (!cardRepo.existsById(id)) {
            throw new ResourceNotFoundException("Card with id " + id + " not found");
        }
        cardRepo.deleteById(id);
    }
}
