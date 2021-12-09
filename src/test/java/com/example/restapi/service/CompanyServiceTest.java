package com.example.restapi.service;

import com.example.restapi.entity.Company;
import com.example.restapi.entity.Employee;
import com.example.restapi.exception.NoCompanyFoundException;
import com.example.restapi.repository.CompanyRepositoryNew;
import com.example.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    CompanyRepositoryNew mockCompanyRepositoryNew;

    @Mock
    EmployeeRepository mockEmployeeRepository;

    @InjectMocks
    CompanyService companyService;

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("1", "Marcus", 22, "Male", 298912220, "1"));
        employees.add(new Employee("2", "Marcus2", 22, "Male", 298912220, "1"));
        employees.add(new Employee("3", "Marcus3", 22, "Male", 298912220, "1"));
        employees.add(new Employee("4", "Marcus4", 22, "Male", 298912220, "1"));
        employees.add(new Employee("5", "Marcus5", 22, "Male", 298912220, "1"));
        employees.add(new Employee("6", "Marcus6", 22, "Male", 298912220, "1"));
        employees.add(new Employee("7", "Marcus7", 22, "Male", 298912220, "1"));
        return employees;
    }

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = new ArrayList<>();


        companies.add(new Company("1", "Spring"));
        companies.add(new Company("2", "Spring2"));

        given(mockCompanyRepositoryNew.findAll())
                .willReturn(companies);
        //when
        List<Company> actual = companyService.findAll();
        //then
        assertEquals(companies, actual);
    }

    @Test
    void should_return_company_when_getById_given_company_and_id() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("1", "Spring"));

        given(mockCompanyRepositoryNew.findById("1"))
                .willReturn(Optional.of(companies.get(0)));

        //when
        Company actual = companyService.findById("1");
        //then
        assertEquals(companies.get(0), actual);
    }

    @Test
    void should_throw_when_find_all_given_company_not_exist() {
        //given
        given(mockCompanyRepositoryNew.findById("9"))
                .willThrow(NoCompanyFoundException.class);

        //when
        //then
        assertThrows(NoCompanyFoundException.class, () -> companyService.findById("9"));
    }

    @Test
    void should_return_employees_when_findEmployeeById_given_employees_and_companies_and_id() {
        //given
        List<Company> companies = new ArrayList<>();
        List<Employee> employees = getEmployees();
        companies.add(new Company("1", "Spring"));
        companies.add(new Company("2", "Spring2"));

        given(mockEmployeeRepository.findAllByCompanyId("1"))
                .willReturn(employees);


        //when
        List<Employee> actual = companyService.findEmployeeById("1");
        //then
        assertEquals(employees, actual);
    }

    @Test
    void should_return_companies_when_getByPage_given_and_companies_and_page_and_pageSize() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("1", "Spring"));
        companies.add(new Company("2", "Spring2"));
        int page = 0;
        int pageSize = 2;
        given(mockCompanyRepositoryNew.findAll(PageRequest.of(page, pageSize)))
                .willReturn(new PageImpl<>(companies));

        //when
        List<Company> actual = companyService.findByPage(page, pageSize);
        //then
        assertEquals(companies, actual);
    }

    @Test
    void should_return_created_company_when_create_given_company() {
        //given
        Company newCompany = new Company(null, "OOCL");
        given(mockCompanyRepositoryNew.insert(newCompany))
                .willReturn(newCompany);

        //when
        Company actual = companyService.create(newCompany);
        //then
        assertEquals(newCompany, actual);
    }

    @Test
    void should_return_updated_company_when_edit_given_company_and_id() {
        //given
        Company existingCompany = new Company("1", "MMM");
        Company updatedCompany = new Company("1", "OOCL");

        given(mockCompanyRepositoryNew.findById("1"))
                .willReturn(Optional.of(existingCompany));
        existingCompany.setName(updatedCompany.getName());

        given(mockCompanyRepositoryNew.save(any(Company.class)))
                .willReturn(updatedCompany);

        //when
        Company actual = companyService.edit("1", existingCompany);
        //then
        assertAll(
                () -> verify(mockCompanyRepositoryNew).save(existingCompany),
                () -> assertEquals(updatedCompany, actual)
        );
    }

    @Test
    void should_return_nothing_when_delete_given_company_and_id() {
        //when
        companyService.delete("1");
        //then
        verify(mockCompanyRepositoryNew).deleteById("1");
    }
}
