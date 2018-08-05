package com.marcinmajkowski.membership.checkin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
class CheckIn {

    @Id
    @GeneratedValue
    // TODO @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long customerId;

    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
