package com.marcinmajkowski.membership.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(createCustomerRequest.getFirstName());
        customer.setLastName(createCustomerRequest.getLastName());
        customer.setCardCode(createCustomerRequest.getCardCode());
        customer.setId(customerRepository.insert(customer));
        return customer;
    }
}
