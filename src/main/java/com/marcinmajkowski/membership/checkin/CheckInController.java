package com.marcinmajkowski.membership.checkin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/check-ins")
class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping
    public CheckIn createCheckIn(@RequestBody CreateCheckInRequest createCheckInRequest) {
        return checkInService.createCheckIn(createCheckInRequest);
    }

    @GetMapping
    public Map<String, List<CheckIn>> getAll() {
        return Collections.singletonMap("checkIns", checkInService.getAll());
    }

    @GetMapping("/{id}")
    public CheckIn getCheckIn(@PathVariable Long id) {
        return checkInService.getCheckIn(id);
    }
}
