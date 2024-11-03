package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.model.CardModel;
import org.example.entity.service.CardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/{id}")
    public CardModel getCardById(@PathVariable Long id) {
        return cardService.findById(id);
    }

    @PostMapping
    public CardModel createCard(@RequestBody CardModel cardModel) {
        return cardService.create(cardModel);
    }
}
