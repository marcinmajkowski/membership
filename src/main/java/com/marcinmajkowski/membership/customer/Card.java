package com.marcinmajkowski.membership.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marcinmajkowski.membership.BaseEntity;

public class Card extends BaseEntity {

    private String code;

    @JsonIgnore
    private Customer customer;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
