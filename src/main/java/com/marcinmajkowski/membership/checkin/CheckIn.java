package com.marcinmajkowski.membership.checkin;

import com.marcinmajkowski.membership.BaseEntity;

import java.time.Instant;

class CheckIn extends BaseEntity {

    private CheckInCustomer customer;

    private Instant timestamp;

    public CheckInCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(CheckInCustomer customer) {
        this.customer = customer;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
