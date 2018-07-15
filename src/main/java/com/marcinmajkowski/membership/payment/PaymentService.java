package com.marcinmajkowski.membership.payment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Transactional
    public Payment createPayment(Long customerId, CreatePaymentForm createPaymentForm) {
        PaymentCustomer customer = new PaymentCustomer();
        customer.setId(customerId);

        Payment payment = new Payment();
        payment.setCustomer(customer);
        payment.setAmount(createPaymentForm.getAmount());
        payment.setTimestamp(Instant.now());
        paymentRepository.storePayment(payment);
        return payment;
    }
}
