package com.marcinmajkowski.membership.company;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies/{companyId}/users")
class CompanyUserController {
    // TODO get company users (only for company admin)
    // TODO add company user (only for company admin)
    // TODO remove company user (only for company admin)
}
