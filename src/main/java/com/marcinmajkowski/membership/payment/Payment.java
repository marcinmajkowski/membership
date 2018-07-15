package com.marcinmajkowski.membership.payment;

import com.marcinmajkowski.membership.BaseEntity;

import java.math.BigDecimal;
import java.time.Instant;

class Payment extends BaseEntity {

    private PaymentCustomer customer;

    private BigDecimal amount;

    private Instant timestamp;

    public PaymentCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(PaymentCustomer customer) {
        this.customer = customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
