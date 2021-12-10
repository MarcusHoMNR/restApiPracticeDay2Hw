package com.example.restapi.service;

import com.example.restapi.entity.Employee;
import com.example.restapi.exception.NoEmployeeFoundException;
import com.example.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository EmployeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>(Collections.singletonList(new Employee("61b1c0ca8093f31e20c3c451", "Marcus", 19, "Male", 1920213, "61b1c0ca8093f31e20c3c451")));
        given(EmployeeRepository.findAll())
                .willReturn(employees);
        //when
        List<Employee> actual = employeeService.findAll();
        //then
        assertEquals(employees, actual);
    }

    @Test
    void should_return_employee_when_getById_given_employees() {
        //given
        Employee employee = new Employee("61b1c0ca8093f31e20c3c451", "Marcus", 19, "Male", 1920213, "61b1c0ca8093f31e20c3c451");

        given(EmployeeRepository.findById("61b1c0ca8093f31e20c3c451"))
                .willReturn(Optional.of(employee));
        //when
        Employee actualEmployee = employeeService.findById("61b1c0ca8093f31e20c3c451");
        //then
        assertEquals(employee, actualEmployee);
    }

    @Test
    void should_throw_when_find_all_given_employee_not_exist() {
        //given
        given(EmployeeRepository.findById("61b1c0ca8093f31e20c3c459"))
                .willThrow(NoEmployeeFoundException.class);

        //when
        //then
        assertThrows(NoEmployeeFoundException.class, () -> employeeService.findById("61b1c0ca8093f31e20c3c459"));
    }

    @Test
    void should_return_employees_when_getByGender_given_employees_and_gender() {
        //given
        List<Employee> employees = new ArrayList<>(Collections.singletonList(new Employee("61b1c0ca8093f31e20c3c451", "Marcus", 19, "Male", 192021, "61b1c0ca8093f31e20c3c451")));

        given(EmployeeRepository.findAllByGender("Male"))
                .willReturn(employees);
        //when
        List<Employee> actualEmployees = employeeService.findByGender("Male");
        //then
        assertEquals(employees, actualEmployees);
    }

    @Test
    void should_return_employees_when_getByPage_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>(Arrays.asList(new Employee("61b1c0ca8093f31e20c3c451", "Marcus", 19, "Male", 1920213, "61b1c0ca8093f31e20c3c451"), new Employee("61b1c0ca8093f31e20c3c452", "Gloria", 19, "Female", 10000, "61b1c0ca8093f31e20c3c451"), new Employee("61b1c0ca8093f31e20c3c453", "Marcus2", 19, "Male", 1920213, "61b1c0ca8093f31e20c3c451")));

        Integer page = 1;
        Integer pageSize = 2;
        given(EmployeeRepository.findAll(PageRequest.of(1, 2)))
                .willReturn(new PageImpl<>(employees));
        //when
        List<Employee> actualEmployees = employeeService.findByPage(page, pageSize);
        //then
        assertEquals(employees, actualEmployees);
    }

    @Test
    void should_return_updated_employee_when_edit_employee_given_updated_employee() {
        //given
        Employee employee = new Employee("61b1c0ca8093f31e20c3c451", "Marcus", 19, "Male", 1920213, "61b1c0ca8093f31e20c3c451");
        Employee updatedEmployee = new Employee("61b1c0ca8093f31e20c3c451", "Marcus", 25, "Male", 9999999, "61b1c0ca8093f31e20c3c452");

        given(EmployeeRepository.findById("61b1c0ca8093f31e20c3c451"))
                .willReturn(Optional.of(employee));

        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setCompanyId(updatedEmployee.getCompanyId());

        given(EmployeeRepository.save(any(Employee.class)))
                .willReturn(updatedEmployee);

        //when
        Employee actual = employeeService.edit("61b1c0ca8093f31e20c3c451", employee);

        //then
        assertAll(
                () -> verify(EmployeeRepository).save(employee),
                () -> assertEquals(updatedEmployee, actual)
        );

    }

    @Test
    void should_return_created_employee_when_add_employee_given_new_employee() {
        //given
        Employee newEmployee = new Employee("61b1c0ca8093f31e20c3c451", "Marcus", 25, "Male", 9999999, "61b1c0ca8093f31e20c3c451");
        given(EmployeeRepository.insert(newEmployee))
                .willReturn(newEmployee);

        //when
        Employee actual = employeeService.create(newEmployee);

        //then
        assertAll(
                () -> verify(EmployeeRepository).insert(newEmployee),
                () -> assertEquals(newEmployee, actual)
        );
    }

    @Test
    void should_return_nothing_when_delete_given_id_employee() {
        //given
        //when
        employeeService.delete("61b1c0ca8093f31e20c3c451");

        //then
        verify(EmployeeRepository).deleteById("61b1c0ca8093f31e20c3c451");
    }
}
