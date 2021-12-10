package com.example.restapi.service;

import com.example.restapi.entity.Company;
import com.example.restapi.entity.Employee;
import com.example.restapi.exception.NoCompanyFoundException;
import com.example.restapi.repository.CompanyRepository;
import com.example.restapi.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(String id) {
        return companyRepository.findById(id).orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> findEmployeeByCompanyId(String id) {
        return employeeRepository.findAllByCompanyId(id);
    }

    public List<Company> findByPage(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company create(Company newCompany) {
        return companyRepository.insert(newCompany);
    }

    public Company edit(String id, Company updatedCompany) {
        Company company = findById(id);

        if (updatedCompany.getName() != null) {
            company.setName(updatedCompany.getName());
        }

        return companyRepository.save(company);
    }

    public void delete(String id) {
        companyRepository.deleteById(id);
    }
}
