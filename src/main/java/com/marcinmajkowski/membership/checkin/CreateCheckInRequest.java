package com.marcinmajkowski.membership.checkin;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class CreateCheckInRequest {

    @NotNull
    private String cardCode;

    private Instant creationInstant;

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
