package com.marcinmajkowski.membership.customer;

import javax.validation.constraints.NotNull;

public class CreateCustomerRequest {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String cardCode;

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

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }
}
