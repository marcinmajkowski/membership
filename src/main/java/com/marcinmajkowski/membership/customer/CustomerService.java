package com.marcinmajkowski.membership.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public List<Customer> getCustomers() {
        return customerRepository.getCustomers();
    }

    @Transactional
    public Customer getCustomer(Long id) {
        return customerRepository.getCustomer(id);
    }

    @Transactional
    public List<Customer> findCustomersByCardCode(String cardCode) {
        return customerRepository.findCustomersByCardCode(cardCode);
    }

    @Transactional
    public Customer createCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(createCustomerRequest.getFirstName());
        customer.setLastName(createCustomerRequest.getLastName());
        customerRepository.storeCustomer(customer);

        if (StringUtils.hasText(createCustomerRequest.getCardCode())) {
            createCard(customer, createCustomerRequest.getCardCode());
        }

        return customer;
    }

    private void createCard(Customer customer, String cardCode) {
        Card card = new Card();
        card.setCode(cardCode);
        customer.addCard(card);
        customerRepository.storeCard(card);
    }
}
