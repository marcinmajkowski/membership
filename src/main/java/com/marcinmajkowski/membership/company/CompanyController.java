package com.marcinmajkowski.membership.company;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public Map<String, List<Company>> getCompanies() {
        return Collections.singletonMap("companies", companyService.getCompanies());
    }

    @PostMapping
    public Company createCompany(CreateCompanyForm createCompanyForm) {
        return companyService.createCompany(createCompanyForm);
    }

    @DeleteMapping("/{companyId}")
    public Company deleteCompany(Long companyId) {
        return companyService.deleteCompany(companyId);
    }

    // TODO update company name (only for company admin)
}
