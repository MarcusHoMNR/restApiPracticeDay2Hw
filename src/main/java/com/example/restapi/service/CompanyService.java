package com.example.restapi.service;

import com.example.restapi.entity.Company;
import com.example.restapi.entity.Employee;
import com.example.restapi.exception.NoCompanyFoundException;
import com.example.restapi.repository.CompanyRepositoryNew;
import com.example.restapi.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private CompanyRepositoryNew companyRepositoryNew;
    private EmployeeRepository employeeRepository;

    public CompanyService(CompanyRepositoryNew companyRepositoryNew, EmployeeRepository employeeRepository) {
        this.companyRepositoryNew = companyRepositoryNew;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> findAll() {
        return companyRepositoryNew.findAll();
    }

    public Company findById(String id) {
        return companyRepositoryNew.findById(id).orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> findEmployeeById(String id) {
        return employeeRepository.findAllByCompanyId(id);
    }

    public List<Company> findByPage(int page, int pageSize) {
        return companyRepositoryNew.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company create(Company newCompany) {
        return companyRepositoryNew.insert(newCompany);
    }

    public Company edit(String id, Company updatedCompany) {
        Company company = findById(id);

        if (updatedCompany.getName() != null) {
            company.setName(updatedCompany.getName());
        }

        return companyRepositoryNew.save(company);
    }

    public void delete(String id) {
        companyRepositoryNew.deleteById(id);
    }
}
