package com.marcinmajkowski.membership.payment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public List<Payment> getAll() {
        return paymentRepository.findAllByOrderByTimestampDesc();
    }

    @Transactional
    public List<Payment> findPaymentsByCustomerId(Long customerId) {
        return paymentRepository.findByCustomerIdOrderByTimestampDesc(customerId);
    }

    @Transactional
    public Payment createPayment(Long customerId, CreatePaymentForm createPaymentForm) {
        Payment payment = new Payment();
        payment.setCustomerId(customerId);
        payment.setAmount(createPaymentForm.getAmount());
        payment.setTimestamp(LocalDateTime.now());
        return paymentRepository.save(payment);
    }
}
