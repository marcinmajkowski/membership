package com.marcinmajkowski.membership.payment;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

class CreatePaymentForm {

    @NotNull
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
