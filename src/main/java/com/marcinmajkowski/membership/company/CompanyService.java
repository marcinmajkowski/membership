package com.marcinmajkowski.membership.company;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    @Transactional(readOnly = true)
    public List<Company> getCompanies() {
        // TODO return for current user only
        return null;
    }

    @Transactional
    public Company createCompany(CreateCompanyForm createCompanyForm) {
        // TODO create company, set current user as admin
        return null;
    }

    @Transactional
    public Company deleteCompany(Long companyId) {
        // TODO check admin permission (here or using annotation), delete company
        return null;
    }
}
