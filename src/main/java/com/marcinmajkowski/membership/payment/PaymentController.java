package com.marcinmajkowski.membership.payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/customers/{customerId}/payments")
    public Payment createPayment(
            @PathVariable Long customerId,
            @Valid @RequestBody CreatePaymentForm createPaymentForm
    ) {
        return paymentService.createPayment(customerId, createPaymentForm);
    }

    @GetMapping("/payments")
    public Map<String, List<Payment>> getAll() {
        return Collections.singletonMap("payments", paymentService.getAll());
    }
}
