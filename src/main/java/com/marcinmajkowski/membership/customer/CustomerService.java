package com.marcinmajkowski.membership.customer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public List<Customer> getCustomers() {
        return customerRepository.getCustomers();
    }

    @Transactional
    public List<Customer> getCustomers(Set<Long> ids) {
        return customerRepository.getCustomers(ids);
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
    public Customer createCustomer(CreateCustomerForm createCustomerForm) {
        Customer customer = new Customer();
        customer.setFirstName(createCustomerForm.getFirstName());
        customer.setLastName(createCustomerForm.getLastName());
        customerRepository.storeCustomer(customer);

        if (StringUtils.hasText(createCustomerForm.getCardCode())) {
            createCard(customer, createCustomerForm.getCardCode());
        }

        return customer;
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteCustomer(id);
    }

    private void createCard(Customer customer, String cardCode) {
        Card card = new Card();
        card.setCode(cardCode);
        customer.addCard(card);
        customerRepository.storeCard(card);
    }
}
