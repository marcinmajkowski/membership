package com.marcinmajkowski.membership.card;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAll() {
        return cardRepository.findAll();
    }

    public Card getCard(String code) {
        return cardRepository.findByCode(code);
    }

    public Card createCard(CreateCardRequest createCardRequest) {
        Card card = new Card();
        card.setCode(createCardRequest.getCode());
        card.setCustomerId(createCardRequest.getCustomerId());
        cardRepository.insert(card);
        return card;
    }
}
