package com.marcinmajkowski.membership.checkin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping("/customers/{customerId}/check-ins")
    public CheckIn createCheckIn(@PathVariable Long customerId) {
        return checkInService.createCheckIn(customerId);
    }

    @GetMapping("/check-ins")
    public Map<String, List<CheckIn>> getAll() {
        return Collections.singletonMap("checkIns", checkInService.getAll());
    }
}
