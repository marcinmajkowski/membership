package com.marcinmajkowski.membership.payment;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface PaymentRepository extends CrudRepository<Payment, Long> {

    List<Payment> findByCustomerIdOrderByTimestampDesc(Long customerId);

    List<Payment> findAllByOrderByTimestampDesc();
}
