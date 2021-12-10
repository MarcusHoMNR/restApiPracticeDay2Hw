package com.example.restapi.service;

import com.example.restapi.entity.Company;
import com.example.restapi.entity.Employee;
import com.example.restapi.exception.NoCompanyFoundException;
import com.example.restapi.repository.CompanyRepository;
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
    CompanyRepository mockCompanyRepository;

    @Mock
    EmployeeRepository mockEmployeeRepository;

    @InjectMocks
    CompanyService companyService;

    private List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("61b1c0ca8093f31e20c3c451", "Marcus", 22, "Male", 298912220, "61b1c0ca8093f31e20c3c451"));
        employees.add(new Employee("61b1c0ca8093f31e20c3c452", "Marcus2", 22, "Male", 298912220, "61b1c0ca8093f31e20c3c451"));
        employees.add(new Employee("61b1c0ca8093f31e20c3c453", "Marcus3", 22, "Male", 298912220, "61b1c0ca8093f31e20c3c451"));
        employees.add(new Employee("61b1c0ca8093f31e20c3c454", "Marcus4", 22, "Male", 298912220, "61b1c0ca8093f31e20c3c451"));
        employees.add(new Employee("61b1c0ca8093f31e20c3c455", "Marcus5", 22, "Male", 298912220, "61b1c0ca8093f31e20c3c451"));
        employees.add(new Employee("61b1c0ca8093f31e20c3c456", "Marcus6", 22, "Male", 298912220, "61b1c0ca8093f31e20c3c451"));
        employees.add(new Employee("61b1c0ca8093f31e20c3c457", "Marcus7", 22, "Male", 298912220, "61b1c0ca8093f31e20c3c451"));
        return employees;
    }

    @Test
    void should_return_all_companies_when_find_all_given_companies() {
        //given
        List<Company> companies = new ArrayList<>();


        companies.add(new Company("61b1c0ca8093f31e20c3c451", "Spring"));
        companies.add(new Company("61b1c0ca8093f31e20c3c452", "Spring2"));

        given(mockCompanyRepository.findAll())
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
        companies.add(new Company("61b1c0ca8093f31e20c3c451", "Spring"));

        given(mockCompanyRepository.findById("61b1c0ca8093f31e20c3c451"))
                .willReturn(Optional.of(companies.get(0)));

        //when
        Company actual = companyService.findById("61b1c0ca8093f31e20c3c451");
        //then
        assertEquals(companies.get(0), actual);
    }

    @Test
    void should_throw_when_find_all_given_company_not_exist() {
        //given
        given(mockCompanyRepository.findById("61b1c0ca8093f31e20c3c499"))
                .willThrow(NoCompanyFoundException.class);

        //when
        //then
        assertThrows(NoCompanyFoundException.class, () -> companyService.findById("61b1c0ca8093f31e20c3c499"));
    }

    @Test
    void should_return_employees_when_findEmployeeById_given_employees_and_companies_and_id() {
        //given
        List<Employee> employees = getEmployees();

        given(mockEmployeeRepository.findAllByCompanyId("61b1c0ca8093f31e20c3c451"))
                .willReturn(employees);


        //when
        List<Employee> actual = companyService.findEmployeeByCompanyId("61b1c0ca8093f31e20c3c451");
        //then
        assertEquals(employees, actual);
    }

    @Test
    void should_return_companies_when_getByPage_given_and_companies_and_page_and_pageSize() {
        //given
        List<Company> companies = new ArrayList<>();
        companies.add(new Company("61b1c0ca8093f31e20c3c451", "Spring"));
        companies.add(new Company("61b1c0ca8093f31e20c3c452", "Spring2"));
        int page = 0;
        int pageSize = 2;
        given(mockCompanyRepository.findAll(PageRequest.of(page, pageSize)))
                .willReturn(new PageImpl<>(companies));

        //when
        List<Company> actual = companyService.findByPage(page, pageSize);
        //then
        assertEquals(companies, actual);
    }

    @Test
    void should_return_created_company_when_create_given_company() {
        //given
        Company newCompany = new Company("61b1c0ca8093f31e20c3c451", "OOCL");
        given(mockCompanyRepository.insert(newCompany))
                .willReturn(newCompany);

        //when
        Company actual = companyService.create(newCompany);
        //then
        assertEquals(newCompany, actual);
    }

    @Test
    void should_return_updated_company_when_edit_given_company_and_id() {
        //given
        Company existingCompany = new Company("61b1c0ca8093f31e20c3c451", "MMM");
        Company updatedCompany = new Company("61b1c0ca8093f31e20c3c451", "OOCL");

        given(mockCompanyRepository.findById("61b1c0ca8093f31e20c3c451"))
                .willReturn(Optional.of(existingCompany));
        existingCompany.setName(updatedCompany.getName());

        given(mockCompanyRepository.save(any(Company.class)))
                .willReturn(updatedCompany);

        //when
        Company actual = companyService.edit("61b1c0ca8093f31e20c3c451", existingCompany);
        //then
        assertAll(
                () -> verify(mockCompanyRepository).save(existingCompany),
                () -> assertEquals(updatedCompany, actual)
        );
    }

    @Test
    void should_return_nothing_when_delete_given_company_and_id() {
        //when
        companyService.delete("61b1c0ca8093f31e20c3c451");
        //then
        verify(mockCompanyRepository).deleteById("61b1c0ca8093f31e20c3c451");
    }
}
