package com.marcinmajkowski.membership.checkin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/check-ins")
class CheckInController {

    @PostMapping
    public CheckIn createCheckIn(CheckIn checkIn) {
        return new CheckIn();
    }

    @GetMapping("/{id}")
    public CheckIn getCheckIn(@PathVariable String id) {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(id);
        checkIn.setCardCode(UUID.randomUUID().toString());
        checkIn.setCreationInstant(Instant.now());
        return checkIn;
    }
}
