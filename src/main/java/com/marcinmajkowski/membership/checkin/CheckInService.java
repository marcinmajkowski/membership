package com.marcinmajkowski.membership.checkin;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
class CheckInService {

    private final CheckInRepository checkInRepository;

    public CheckInService(CheckInRepository checkInRepository) {
        this.checkInRepository = checkInRepository;
    }

    public List<CheckIn> getAll() {
        return checkInRepository.findAll();
    }

    public CheckIn getCheckIn(Long id) {
        return checkInRepository.findById(id);
    }

    public CheckIn createCheckIn(CreateCheckInRequest createCheckInRequest) {
        Instant creationInstant = Optional.ofNullable(createCheckInRequest.getCreationInstant())
                .orElseGet(Instant::now);

        CheckIn checkIn = new CheckIn();
        checkIn.setCardCode(createCheckInRequest.getCardCode());
        checkIn.setCreationInstant(creationInstant);
        checkIn.setId(checkInRepository.insert(checkIn));
        return checkIn;
    }
}
