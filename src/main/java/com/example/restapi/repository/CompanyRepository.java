package com.example.restapi.repository;

import com.example.restapi.entity.Company;
import com.example.restapi.entity.Employee;
import com.example.restapi.exception.NoCompanyFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    @Autowired
    EmployeeRepository employeeRepository;

    private List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        this.companies.add(new Company("1", "Spring"));
        this.companies.add(new Company("2", "Spring2"));
        this.companies.add(new Company("3", "Spring3"));
    }


    public List<Company> findAll() {
        return this.companies;
    }

    public Company findById(String id) {
        return this.companies.stream().filter(company1 -> company1.getId().equals(id)).findFirst().orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> findEmployeeById(String id) {
        return employeeRepository.findByCompanyId(id);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {

        return this.companies.stream().skip((long) (page - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }

    public Company create(Company newCompany) {
        String nextId = String.valueOf(this.companies.stream().mapToInt(company -> Integer.parseInt(company.getId())).max().orElse(0) + 1);
        newCompany.setId(nextId);
        this.companies.add(newCompany);
        return newCompany;
    }


    public Company save(String id, Company updatedCompany) {
        Company company = findById(id);
        companies.remove(company);
        companies.add(updatedCompany);
        return updatedCompany;
    }

    public void delete(String id) {
        Company company = findById(id);
        companies.remove(company);
    }

    public void clearAll() {
        companies.clear();
    }
}
