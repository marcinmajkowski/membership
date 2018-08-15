package com.marcinmajkowski.membership.company;

import javax.validation.constraints.NotBlank;

class CreateCompanyForm {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
