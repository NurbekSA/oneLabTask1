package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.persistence.model.dto.CardModelDTO;
import org.example.persistence.service.CardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/{id}")
    public CardModelDTO getCardById(@PathVariable Long id) {
        return cardService.findById(id);
    }

    @PostMapping
    public CardModelDTO createCard(@RequestBody CardModelDTO cardModel) {
        return cardService.create(cardModel);
    }
}
