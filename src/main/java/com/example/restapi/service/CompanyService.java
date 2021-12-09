package com.example.restapi.service;

import com.example.restapi.entity.Company;
import com.example.restapi.entity.Employee;
import com.example.restapi.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(String id) {
        return companyRepository.findById(id);
    }

    public List<Employee> findEmployeeById(String id) {
        return companyRepository.findEmployeeById(id);
    }

    public List<Company> findByPage(int page, int pageSize) {
        return companyRepository.findByPage(page, pageSize);
    }

    public Company create(Company newCompany) {
        return companyRepository.create(newCompany);
    }

    public Company edit(String id, Company updatedCompany) {
        Company company = companyRepository.findById(id);

        if (updatedCompany.getName() != null) {
            company.setName(updatedCompany.getName());
        }

        return companyRepository.save(id, company);
    }

    public void delete(String id) {
        companyRepository.delete(id);
    }
}
