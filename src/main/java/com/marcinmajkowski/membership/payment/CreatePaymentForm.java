package com.marcinmajkowski.membership.payment;

import java.math.BigDecimal;

class CreatePaymentForm {

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
