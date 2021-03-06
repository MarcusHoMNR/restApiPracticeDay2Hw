package com.example.restapi.entity;


import com.example.restapi.entity.Employee;

import java.util.List;

public class Company {
    private Integer id;
    private String name;
    private List<Employee> employees;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee>  employee) {
        this.employees = employee;
    }

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
