package com.example.restapi.dto;

import java.util.List;

public class CompanyResponse {
    private String id;
    private String name;
    private List<EmployeeResponse> employees;

    public CompanyResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeResponse> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponse> employeeResponses) {
        this.employees = employeeResponses;
    }
}
