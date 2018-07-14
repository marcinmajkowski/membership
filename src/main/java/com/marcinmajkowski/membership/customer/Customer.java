package com.marcinmajkowski.membership.customer;

import com.marcinmajkowski.membership.BaseEntity;

import java.util.HashSet;
import java.util.Set;

class Customer extends BaseEntity {

    private String firstName;

    private String lastName;

    private Set<Card> cards = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
        card.setCustomer(this);
    }
}
