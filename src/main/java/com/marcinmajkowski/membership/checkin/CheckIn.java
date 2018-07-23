package com.marcinmajkowski.membership.checkin;

import com.marcinmajkowski.membership.BaseEntity;
import com.marcinmajkowski.membership.customer.CustomerReference;

import java.time.Instant;

class CheckIn extends BaseEntity {

    private CustomerReference customer;

    private Instant timestamp;

    public CustomerReference getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerReference customer) {
        this.customer = customer;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
