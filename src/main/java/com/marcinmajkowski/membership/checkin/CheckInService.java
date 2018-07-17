package com.marcinmajkowski.membership.checkin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
class CheckInService {

    private final CheckInRepository checkInRepository;

    public CheckInService(CheckInRepository checkInRepository) {
        this.checkInRepository = checkInRepository;
    }

    @Transactional
    public List<CheckIn> findCheckInsByCustomerId(Long customerId) {
        return checkInRepository.findCheckInsByCustomerId(customerId);
    }

    @Transactional
    public List<CheckIn> getAll() {
        return checkInRepository.findAll();
    }

    @Transactional
    public CheckIn createCheckIn(Long customerId) {
        CheckInCustomer customer = new CheckInCustomer();
        customer.setId(customerId);

        CheckIn checkIn = new CheckIn();
        checkIn.setCustomer(customer);
        checkIn.setTimestamp(Instant.now());
        checkInRepository.storeCheckIn(checkIn);
        return checkIn;
    }

    @Transactional
    public void deleteCheckIn(Long checkInId) {
        checkInRepository.deleteCheckIn(checkInId);
    }
}
