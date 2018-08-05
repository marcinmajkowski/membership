package com.marcinmajkowski.membership.checkin;

import com.marcinmajkowski.membership.customer.Customer;
import com.marcinmajkowski.membership.customer.CustomerService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
class CheckInController {

    private final CheckInService checkInService;

    private final CustomerService customerService;

    public CheckInController(CheckInService checkInService, CustomerService customerService) {
        this.checkInService = checkInService;
        this.customerService = customerService;
    }

    @PostMapping("/customers/{customerId}/check-ins")
    public CheckIn createCheckIn(@PathVariable Long customerId) {
        return checkInService.createCheckIn(customerId);
    }

    @GetMapping("/customers/{customerId}/check-ins")
    public Map<String, List<CheckIn>> getCustomerCheckIns(@PathVariable Long customerId) {
        return Collections.singletonMap("checkIns", checkInService.findCheckInsByCustomerId(customerId));
    }

    // TODO move implementation to service
    // TODO response class
    @Transactional
    @GetMapping("/check-ins")
    public Map<String, List> getAll() {
        List<CheckIn> checkIns = checkInService.getAll();
        Set<Long> customerIds = checkIns.stream()
                .map(CheckIn::getCustomerId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        List<Customer> customers = customerService.getCustomers(customerIds);
        Map<String, List> response = new HashMap<>();
        response.put("checkIns", checkIns);
        response.put("customers", customers);
        return response;
    }

    @DeleteMapping("/check-ins/{checkInId}")
    public CheckIn deleteCheckIn(@PathVariable Long checkInId) {
        return checkInService.deleteCheckIn(checkInId);
    }
}
