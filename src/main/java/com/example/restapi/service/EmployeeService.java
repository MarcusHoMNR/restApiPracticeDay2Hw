package com.example.restapi.service;

import com.example.restapi.entity.Employee;
import com.example.restapi.exception.NoEmployeeFoundException;
import com.example.restapi.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    public Employee findById(String id) {
        return employeeRepository.findById(id).orElseThrow(NoEmployeeFoundException::new);
    }


    public List<Employee> findByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee edit(String id, Employee updatedEmployee) {
        Employee employee = findById(id);
        if (updatedEmployee.getAge() != null) {
            employee.setAge(updatedEmployee.getAge());
        }
        if (updatedEmployee.getSalary() != null) {
            employee.setSalary(updatedEmployee.getSalary());
        }

        if (updatedEmployee.getCompanyId() != null) {
            employee.setCompanyId(updatedEmployee.getCompanyId());
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> findByPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Employee create(Employee newEmployee) {
        return employeeRepository.insert(newEmployee);
    }

    public void delete(String id) {
        employeeRepository.deleteById(id);
    }
}
