package com.marcinmajkowski.membership.checkin;

import org.springframework.web.bind.annotation.*;

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
