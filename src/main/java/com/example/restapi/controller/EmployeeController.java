package com.example.restapi.controller;

import com.example.restapi.entity.Employee;
import com.example.restapi.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private EmployeeService employeeService;

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
    public Employee createEmployee(@RequestBody Employee newEmployee) {
        return employeeService.create(newEmployee);
    }

    @PutMapping("/{id}")
    public Employee editEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        return employeeService.edit(id, updatedEmployee);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable String id) {
        employeeService.delete(id);
    }
}
