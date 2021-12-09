package com.example.restapi.controller;

import com.example.restapi.dto.CompanyResponse;
import com.example.restapi.dto.EmployeeResponse;
import com.example.restapi.entity.Company;
import com.example.restapi.entity.Employee;
import com.example.restapi.mapper.CompanyMapper;
import com.example.restapi.mapper.EmployeeMapper;
import com.example.restapi.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("companies")
public class CompanyController {

    private CompanyService companyService;
    private CompanyMapper companyMapper;
    private EmployeeMapper employeeMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper, EmployeeMapper employeeMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<CompanyResponse> getAllCompanies() {
        return companyService.findAll().stream().map(company -> companyMapper.toResponse(company, companyService.findEmployeeById(company.getId()))).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompanyById(@PathVariable String id) {
        Company company = companyService.findById(id);
        List<Employee> employees = companyService.findEmployeeById(id);
        return companyMapper.toResponse(company, employees);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getAllEmployeesByCompanyId(@PathVariable String id) {
        return companyService.findEmployeeById(id).stream().map(employee -> employeeMapper.toResponse(employee)).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getAllCompaniesByPage(@RequestParam Integer page, Integer pageSize) {
        return companyService.findByPage(page, pageSize).stream().map(company -> companyMapper.toResponse(company, companyService.findEmployeeById(company.getId()))).collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public CompanyResponse createCompany(@RequestBody Company newCompany) {
        Company company = companyService.create(newCompany);
        List<Employee> employees = companyService.findEmployeeById(company.getId());
        return companyMapper.toResponse(company, employees);
    }

    @PutMapping("/{id}")
    public CompanyResponse editCompany(@PathVariable String id, @RequestBody Company updatedCompany) {
        Company company = companyService.edit(id, updatedCompany);
        List<Employee> employees = companyService.findEmployeeById(id);
        return companyMapper.toResponse(company, employees);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable String id) {
        companyService.delete(id);
    }
}
