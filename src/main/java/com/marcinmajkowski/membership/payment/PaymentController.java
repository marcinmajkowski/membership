package com.marcinmajkowski.membership.payment;

import com.marcinmajkowski.membership.customer.Customer;
import com.marcinmajkowski.membership.customer.CustomerService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
class PaymentController {

    private final PaymentService paymentService;

    private final CustomerService customerService;

    public PaymentController(PaymentService paymentService, CustomerService customerService) {
        this.paymentService = paymentService;
        this.customerService = customerService;
    }

    @PostMapping("/customers/{customerId}/payments")
    public Payment createPayment(
            @PathVariable Long customerId,
            @Valid @RequestBody CreatePaymentForm createPaymentForm
    ) {
        return paymentService.createPayment(customerId, createPaymentForm);
    }


    @GetMapping("/customers/{customerId}/payments")
    public Map<String, List<Payment>> getCustomerPayments(@PathVariable Long customerId) {
        return Collections.singletonMap("payments", paymentService.findPaymentsByCustomerId(customerId));
    }

    // TODO move implementation to service
    // TODO response class
    @Transactional
    @GetMapping("/payments")
    public Map<String, List> getAll() {
        List<Payment> payments = paymentService.getAll();
        Set<Long> customerIds = payments.stream()
                .map(Payment::getCustomerId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        List<Customer> customers = customerService.getCustomers(customerIds);
        Map<String, List> response = new HashMap<>();
        response.put("payments", payments);
        response.put("customers", customers);
        return response;
    }

    @DeleteMapping("/payments/{paymentId}")
    public Payment deleteCheckIn(@PathVariable Long paymentId) {
        return paymentService.deletePayment(paymentId);
    }
}
