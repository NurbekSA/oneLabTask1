package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.model.CardModel;
import org.example.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/{id}")
    public ResponseEntity<CardModel> getCardById(@PathVariable Long id) {
        return cardService.findById(id);
    }

    @PostMapping
    public HttpStatusCode createCard(@RequestBody CardModel cardModel) {
        return cardService.create(cardModel).getStatusCode();
    }
}
