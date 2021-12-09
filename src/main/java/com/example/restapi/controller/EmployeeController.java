package com.example.restapi.controller;

import com.example.restapi.dto.EmployeeRequest;
import com.example.restapi.entity.Employee;
import com.example.restapi.mapper.EmployeeMapper;
import com.example.restapi.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }


    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable String id) {
        return employeeService.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getAllEmployeesByGender(@RequestParam String gender) {
        return employeeService.findByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getAllEmployeesByPage(@RequestParam Integer page, Integer pageSize) {
        return employeeService.findByPage(page, pageSize);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeRequest newEmployeeRequest) {
        Employee newEmployee = employeeMapper.toEntity(newEmployeeRequest);
        return employeeService.create(newEmployee);
    }

    @PutMapping("/{id}")
    public Employee editEmployee(@PathVariable String id, @RequestBody EmployeeRequest updatedEmployeeRequest) {
        Employee updatedEmployee = employeeMapper.toEntity(updatedEmployeeRequest);
        return employeeService.edit(id, updatedEmployee);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.delete(id);
    }
}
