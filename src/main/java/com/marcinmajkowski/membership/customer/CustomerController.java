package com.marcinmajkowski.membership.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody CreateCustomerForm createCustomerForm) {
        return customerService.createCustomer(createCustomerForm);
    }

    @GetMapping
    public Map<String, List<Customer>> getCustomers(
            @RequestParam(value = "card_code", required = false) String cardCode
    ) {
        List<Customer> customers;
        if (cardCode != null) {
            customers = customerService.findCustomersByCardCode(cardCode);
        } else {
            customers = customerService.getCustomers();
        }
        return Collections.singletonMap("customers", customers);
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }
}
