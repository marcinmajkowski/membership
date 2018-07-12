package com.marcinmajkowski.membership.card;

import javax.validation.constraints.NotNull;

public class CreateCardRequest {

    @NotNull
    private String code;

    @NotNull
    private Long customerId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
