package com.example.restapi.repository;

import com.example.restapi.entity.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class EmployeeRepositoryTest {
    @InjectMocks
    EmployeeRepository employeeRepository;

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Marcus", 22, "Male", 298912220, 1));
        employees.add(new Employee(2, "Marcus2", 22, "Male", 298912220, 1));
        employees.add(new Employee(3, "Marcus3", 22, "Male", 298912220, 1));
        employees.add(new Employee(4, "Marcus4", 22, "Male", 298912220, 1));
        employees.add(new Employee(5, "Marcus5", 22, "Male", 298912220, 1));
        employees.add(new Employee(6, "Marcus6", 22, "Male", 298912220, 1));
        employees.add(new Employee(7, "Marcus7", 22, "Male", 298912220, 1));
        return employees;
    }

    @Test
    void should_return_employees_when_findAll_given_employees() {
        //given
        List<Employee> employees = getEmployees();

        //when
        List<Employee> actual = employeeRepository.findAll();

        //then
        assertEquals(employees.size(), actual.size());
        assertEquals(employees.get(0).getId(), actual.get(0).getId());
        assertEquals(employees.get(1).getId(), actual.get(1).getId());
        assertEquals(employees.get(2).getId(), actual.get(2).getId());
        assertEquals(employees.get(3).getId(), actual.get(3).getId());
        assertEquals(employees.get(4).getId(), actual.get(4).getId());
        assertEquals(employees.get(5).getId(), actual.get(5).getId());
        assertEquals(employees.get(6).getId(), actual.get(6).getId());
    }

    @Test
    void should_return_employee_when_findById_given_employe_and_id() {
        //given
        List<Employee> employees = getEmployees();

        //when
        Employee actual = employeeRepository.findById(1);

        //then
        assertNotNull(actual);
        assertEquals(employees.get(0).getId(), actual.getId());
    }

}
