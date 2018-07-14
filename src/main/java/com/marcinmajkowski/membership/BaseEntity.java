package com.marcinmajkowski.membership;

import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO auditing, deleting
public class BaseEntity {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }
}
