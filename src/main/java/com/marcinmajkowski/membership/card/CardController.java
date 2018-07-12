package com.marcinmajkowski.membership.card;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cards")
class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public Card createCard(@RequestBody CreateCardRequest createCardRequest) {
        return cardService.createCard(createCardRequest);
    }

    @GetMapping
    public Map<String, List<Card>> getAll() {
        return Collections.singletonMap("cards", cardService.getAll());
    }

    @GetMapping("/{code}")
    public Card getCard(@PathVariable String code) {
        return cardService.getCard(code);
    }
}
