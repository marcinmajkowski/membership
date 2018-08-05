package com.marcinmajkowski.membership.checkin;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface CheckInRepository extends CrudRepository<CheckIn, Long> {

    List<CheckIn> findByCustomerIdOrderByTimestampDesc(Long customerId);

    List<CheckIn> findAllByOrderByTimestampDesc();
}
