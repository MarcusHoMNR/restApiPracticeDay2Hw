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

    public Company findById(int id) {
        return companyRepository.findById(id);
    }

    public List<Employee> findEmployeeById(int id) {
        return companyRepository.findEmployeeById(id);
    }

    public List<Company> findByPage(int page, int pageSize) {
        return companyRepository.findByPage(page, pageSize);
    }

    public Company create(Company newCompany) {
        return null;
    }
}
