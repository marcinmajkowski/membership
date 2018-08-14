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
    public List<CheckIn> getCustomerFirst20BeforeTimestamp(Long customerId, LocalDateTime timestamp) {
        LocalDateTime beforeTimestamp = timestamp != null ? timestamp : LocalDateTime.now();
        return checkInRepository.findFirst20ByCustomerIdAndTimestampBeforeOrderByTimestampDesc(customerId, beforeTimestamp);
    }

    @Transactional(readOnly = true)
    public List<CheckIn> getFirst20BeforeTimestamp(LocalDateTime timestamp) {
        LocalDateTime beforeTimestamp = timestamp != null ? timestamp : LocalDateTime.now();
        return checkInRepository.findFirst20ByTimestampBeforeOrderByTimestampDesc(beforeTimestamp);
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
