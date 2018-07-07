package com.marcinmajkowski.membership.checkin;

import java.time.Instant;

class CheckIn {

    private String id;

    private String cardCode;

    private Instant creationInstant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Instant getCreationInstant() {
        return creationInstant;
    }

    public void setCreationInstant(Instant creationInstant) {
        this.creationInstant = creationInstant;
    }
}
