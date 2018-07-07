package com.marcinmajkowski.membership.checkin;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public CheckIn getCheckIn(@PathVariable Long id) {
        return checkInService.getCheckIn(id);
    }
}
