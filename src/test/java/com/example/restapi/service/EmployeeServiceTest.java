package com.example.restapi.service;

import com.example.restapi.entity.Employee;
import com.example.restapi.exception.NoEmployeeFoundException;
import com.example.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository mockEmployeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>(Collections.singletonList(new Employee("1", "Marcus", 19, "Male", 1920213, "1")));
        given(mockEmployeeRepository.findAll())
                .willReturn(employees);
        //when
        List<Employee> actual = employeeService.findAll();
        //then
        assertEquals(employees, actual);
    }

    @Test
    void should_return_employee_when_getById_given_employees() {
        //given
        Employee employee = new Employee("1", "Marcus", 19, "Male", 1920213, "1");

        given(mockEmployeeRepository.findById("1"))
                .willReturn(employee);
        //when
        Employee actualEmployee = employeeService.findById("1");
        //then
        assertEquals(employee, actualEmployee);
    }

    @Test
    void should_throw_when_find_all_given_employee_not_exist() {
        //given
        given(mockEmployeeRepository.findById("9"))
                .willThrow(NoEmployeeFoundException.class);

        //when
        //then
        assertThrows(NoEmployeeFoundException.class, () -> employeeService.findById("9"));
    }

    @Test
    void should_return_employees_when_getByGender_given_employees_and_gender() {
        //given
        List<Employee> employees = new ArrayList<>(Collections.singletonList(new Employee("1", "Marcus", 19, "Male", 192021, "13")));

        given(mockEmployeeRepository.findByGender("Male"))
                .willReturn(employees);
        //when
        List<Employee> actualEmployees = employeeService.findByGender("Male");
        //then
        assertEquals(employees, actualEmployees);
    }

    @Test
    void should_return_employees_when_getByPage_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>(Arrays.asList(new Employee("1", "Marcus", 19, "Male", 1920213, "1"), new Employee("2", "Gloria", 19, "Female", 10000, "1"), new Employee("3", "Marcus2", 19, "Male", 1920213, "1")));

        Integer page = 1;
        Integer pageSize = 2;
        given(mockEmployeeRepository.findByPage(page, pageSize))
                .willReturn(employees);
        //when
        List<Employee> actualEmployees = employeeService.findByPage(page, pageSize);
        //then
        assertEquals(employees, actualEmployees);
    }

    @Test
    void should_return_updated_employee_when_edit_employee_given_updated_employee() {
        //given
        Employee employee = new Employee("1", "Marcus", 19, "Male", 1920213, "1");
        Employee updatedEmployee = new Employee("1", "Marcus", 25, "Male", 9999999, "2");

        given(mockEmployeeRepository.findById("1"))
                .willReturn(employee);

        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setCompanyId(updatedEmployee.getCompanyId());

        given(mockEmployeeRepository.save(eq("1"), any(Employee.class)))
                .willReturn(updatedEmployee);

        //when
        Employee actual = employeeService.edit("1", employee);

        //then
        assertAll(
                () -> verify(mockEmployeeRepository).save(employee.getId(), employee),
                () -> assertEquals(updatedEmployee, actual)
        );

    }

    @Test
    void should_return_created_employee_when_add_employee_given_new_employee() {
        //given
        Employee newEmployee = new Employee("1", "Marcus", 25, "Male", 9999999, "1");
        given(mockEmployeeRepository.create(newEmployee))
                .willReturn(newEmployee);

        //when
        Employee actual = employeeService.create(newEmployee);

        //then
        assertAll(
                () -> verify(mockEmployeeRepository).create(newEmployee),
                () -> assertEquals(newEmployee, actual)
        );
    }

    @Test
    void should_return_nothing_when_delete_given_id_employee() {
        //given
        //when
        employeeService.delete("1");

        //then
        verify(mockEmployeeRepository).delete("1");
    }
}
