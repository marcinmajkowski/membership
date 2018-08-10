package com.marcinmajkowski.membership.checkin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
class CheckInService {

    private final CheckInRepository checkInRepository;

    public CheckInService(CheckInRepository checkInRepository) {
        this.checkInRepository = checkInRepository;
    }

    @Transactional(readOnly = true)
    public List<CheckIn> findCheckInsByCustomerId(Long customerId) {
        return checkInRepository.findByCustomerIdOrderByTimestampDesc(customerId);
    }

    @Transactional(readOnly = true)
    public List<CheckIn> getFirst20BeforeTimestamp(LocalDateTime timestamp) {
        if (timestamp != null) {
            return checkInRepository.findFirst20ByTimestampBeforeOrderByTimestampDesc(timestamp);
        } else {
            return checkInRepository.findFirst20ByTimestampBeforeOrderByTimestampDesc(LocalDateTime.now());
        }
    }

    @Transactional
    public CheckIn createCheckIn(Long customerId) {
        CheckIn checkIn = new CheckIn();
        checkIn.setCustomerId(customerId);
        checkIn.setTimestamp(LocalDateTime.now());
        return checkInRepository.save(checkIn);
    }

    @Transactional
    public CheckIn deleteCheckIn(Long checkInId) {
        CheckIn checkIn = checkInRepository.findById(checkInId).get();
        checkInRepository.delete(checkIn);
        return checkIn;
    }
}
