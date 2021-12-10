package com.example.restapi.controller;

import com.example.restapi.entity.Company;
import com.example.restapi.entity.Employee;
import com.example.restapi.repository.CompanyRepository;
import com.example.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void cleanRepository() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_get_all_companies_when_perform_get_given_companies() throws Exception {
        //given
        Company company1 = new Company("61b1c0ca8093f31e20c3c451", "Spring");
        Company company2 = new Company("61b1c0ca8093f31e20c3c452", "Spring2");

        companyRepository.insert(company1);
        companyRepository.insert(company2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Spring"))
                .andExpect(jsonPath("$[0].employees").doesNotExist());
    }

    @Test
    void should_get_company_when_perform_getById_given_company_and_id() throws Exception {
        //given
        Company company1 = new Company("61b1c0ca8093f31e20c3c451", "Spring");

        companyRepository.insert(company1);

        Employee employee = new Employee("61b1bb92297d450797ed4261", "Marcus", 19, "Male", 1920213, company1.getId());
        Employee employee2 = new Employee("61b1bb92297d450797ed4262", "Gloria", 22, "Female", 1000000, company1.getId());

        employeeRepository.insert(employee);
        employeeRepository.insert(employee2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}", company1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("Spring"))
                .andExpect(jsonPath("$.employees[0].name").value("Marcus"))
                .andExpect(jsonPath("$.employees[0].gender").value("Male"));
    }

    @Test
    void should_get_all_employee_under_company_when_obtain_employee_list_given_employees_and_company() throws Exception {
        //given
        Company company1 = new Company("61b1c0ca8093f31e20c3c451", "Spring");

        companyRepository.insert(company1);

        Employee employee = new Employee("61b1bb92297d450797ed4261", "Marcus", 19, "Male", 1920213, company1.getId());
        Employee employee2 = new Employee("61b1bb92297d450797ed4262", "Gloria", 22, "Female", 1000000, company1.getId());

        employeeRepository.insert(employee);
        employeeRepository.insert(employee2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", company1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Marcus"))
                .andExpect(jsonPath("$[0].age").value("19"))
                .andExpect(jsonPath("$[0].gender").value("Male"));
    }

    @Test
    void should_get_all_companies_when_getByPaging_given_page_and_pageSize_and_company() throws Exception {
        Company company1 = new Company("61b1c0ca8093f31e20c3c451", "Spring");
        Company company2 = new Company("61b1c0ca8093f31e20c3c452", "Spring2");
        Company company3 = new Company("61b1c0ca8093f31e20c3c453", "Spring3");

        companyRepository.insert(company1);
        companyRepository.insert(company2);
        companyRepository.insert(company3);

        String page = "0";
        String pageSize = "2";

        mockMvc.perform(MockMvcRequestBuilders.get("/companies?page=" + page + "&pageSize=" + pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Spring"))
                .andExpect(jsonPath("$[0].employees").doesNotExist())
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].name").value("Spring2"))
                .andExpect(jsonPath("$[1].employees").doesNotExist());
    }

    @Test
    void should_return_company_when_perform_post_given_company() throws Exception {
        //given
        String company = "{\n" +
                "    \"name\": \"OOCL\"\n" +
                "}";

        //when
        //then
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(company))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("OOCL"))
                .andExpect(jsonPath("$.employees").doesNotExist());
    }

    @Test
    void should_return_changed_company_when_perform_put_given_company_id() throws Exception {
        //given
        Company company1 = new Company("61b1c0ca8093f31e20c3c451", "Spring");
        Company company2 = new Company("61b1c0ca8093f31e20c3c452", "Spring2");
        Company company3 = new Company("61b1c0ca8093f31e20c3c453", "Spring3");

        companyRepository.insert(company1);
        companyRepository.insert(company2);
        companyRepository.insert(company3);

        String company = "{\n" +
                "    \"name\": \"OOCL\"\n" +
                "}";

        //when
        //then
        mockMvc.perform(put("/companies/{id}", company1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(company))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("OOCL"))
                .andExpect(jsonPath("$.employees").doesNotExist());
    }

    @Test
    void should_delete_company_when_perform_delete_given_company_and_id() throws Exception {
        //given
        Company company1 = new Company("61b1c0ca8093f31e20c3c451", "Spring");
        Company company2 = new Company("61b1c0ca8093f31e20c3c452", "Spring2");
        Company company3 = new Company("61b1c0ca8093f31e20c3c4513", "Spring3");

        companyRepository.insert(company1);
        companyRepository.insert(company2);
        companyRepository.insert(company3);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", company1.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}