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

    private List<Employee> getExpectedEmployees() {
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
        List<Employee> expectedEmployees = getExpectedEmployees();

        //when
        List<Employee> actual = employeeRepository.findAll();

        //then
        assertEquals(expectedEmployees.size(), actual.size());
        assertEquals(expectedEmployees.get(0).getId(), actual.get(0).getId());
        assertEquals(expectedEmployees.get(1).getId(), actual.get(1).getId());
        assertEquals(expectedEmployees.get(2).getId(), actual.get(2).getId());
        assertEquals(expectedEmployees.get(3).getId(), actual.get(3).getId());
        assertEquals(expectedEmployees.get(4).getId(), actual.get(4).getId());
        assertEquals(expectedEmployees.get(5).getId(), actual.get(5).getId());
        assertEquals(expectedEmployees.get(6).getId(), actual.get(6).getId());
    }

    @Test
    void should_return_employee_when_findById_given_employee_and_id() {
        //given
        List<Employee> expectedEmployees = getExpectedEmployees();

        //when
        Employee actual = employeeRepository.findById(1);

        //then
        assertNotNull(actual);
        assertEquals(expectedEmployees.get(0).getId(), actual.getId());
    }

    @Test
    void should_return_employees_when_findByGender_given_employees_and_gender() {
        //given
        List<Employee> expectedEmployees = getExpectedEmployees();

        //when
        List<Employee> actual = employeeRepository.findByGender("Male");

        //then
        assertEquals(expectedEmployees.size(), actual.size());
        assertEquals(expectedEmployees.get(0).getId(), actual.get(0).getId());
        assertEquals(expectedEmployees.get(1).getId(), actual.get(1).getId());
        assertEquals(expectedEmployees.get(2).getId(), actual.get(2).getId());
        assertEquals(expectedEmployees.get(3).getId(), actual.get(3).getId());
        assertEquals(expectedEmployees.get(4).getId(), actual.get(4).getId());
        assertEquals(expectedEmployees.get(5).getId(), actual.get(5).getId());
        assertEquals(expectedEmployees.get(6).getId(), actual.get(6).getId());

    }

    @Test
    void should_return_employees_when_findByPage_given_employees_and_page_and_pageSize() {
        //given
        List<Employee> expectedEmployees = getExpectedEmployees();

        //when
        List<Employee> actual = employeeRepository.findByPage(1, 2);

        //then
        assertEquals(2, actual.size());
        assertEquals(expectedEmployees.get(0).getId(), actual.get(0).getId());
        assertEquals(expectedEmployees.get(1).getId(), actual.get(1).getId());
    }

    @Test
    void should_return_created_employee_when_create_given_employee() {
        //given
        Employee employee = new Employee(1, "SOME", 19, "Female", 1212, 1);
        //when
        Employee actual = employeeRepository.create(employee);

        //then
        assertNotNull(actual);
        assertEquals(8, actual.getId());
    }

    @Test
    void should_return_changed_employee_when_save_given_employee() {
        //given

        //when
        Employee actual = employeeRepository.save(2, new Employee(2, "SOME", 19, "Female", 1212, 1));

        //then
        assertNotNull(actual);
        assertEquals(2, actual.getId());
        assertEquals("SOME", actual.getName());
        assertEquals(19, actual.getAge());
        assertEquals("Female", actual.getGender());
        assertEquals(1212, actual.getSalary());
        assertEquals(1, actual.getCompanyId());
    }

    @Test
    void should_return_nothing_when_delete_given_employee() {
        //given
        //when
        employeeRepository.delete(3);

        //then
        assertEquals(6, employeeRepository.findAll().size());
    }

}
