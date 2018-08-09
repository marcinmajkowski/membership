package com.marcinmajkowski.membership.checkin;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

interface CheckInRepository extends CrudRepository<CheckIn, Long> {

    List<CheckIn> findByCustomerIdOrderByTimestampDesc(Long customerId);

    List<CheckIn> findAllByOrderByTimestampDesc();

    List<CheckIn> findFirst20ByTimestampBeforeOrderByTimestampDesc(LocalDateTime timestamp);
}
