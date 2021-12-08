package com.example.restapi.repository;

import com.example.restapi.entity.Company;
import com.example.restapi.entity.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CompanyRepositoryTest {
    @InjectMocks
    CompanyRepository companyRepository;

    @Mock
    EmployeeRepository mockEmployeeRepository;

    private List<Company> getExpectedCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "Spring"));
        companies.add(new Company(2, "Spring2"));
        companies.add(new Company(3, "Spring3"));
        return companies;
    }

    private List<Employee> getEmployeesFromCompany1() {
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
    void should_return_companies_when_findAll_given_companies() {
        //given
        List<Company> expectedCompanies = getExpectedCompanies();
        given(mockEmployeeRepository.findByCompanyId(1))
                .willReturn(getEmployeesFromCompany1());
        //when
        List<Company> actual = companyRepository.findAll();

        //then
        assertEquals(expectedCompanies.size(), actual.size());
        assertEquals(expectedCompanies.get(0).getId(), actual.get(0).getId());
        assertEquals(expectedCompanies.get(1).getId(), actual.get(1).getId());
        assertEquals(expectedCompanies.get(2).getId(), actual.get(2).getId());
    }
}
