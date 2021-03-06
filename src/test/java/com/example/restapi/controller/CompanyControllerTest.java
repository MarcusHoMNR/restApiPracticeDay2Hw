package com.example.restapi.controller;

import com.example.restapi.entity.Company;
import com.example.restapi.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void cleanRepository() {
        companyRepository.clearAll();
    }

    @Test
    void should_get_all_companies_when_perform_get_given_companies() throws Exception {
        //given
        Company company1 = new Company(1, "Spring");
        Company company2 = new Company(2, "Spring2");

        companyRepository.create(company1);
        companyRepository.create(company2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Spring"))
                .andExpect(jsonPath("$[0].employees[0].id").isNumber())
                .andExpect(jsonPath("$[0].employees[0].name").value("Marcus"))
                .andExpect(jsonPath("$[0].employees[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].employees[0].salary").value("298912220"))
                .andExpect(jsonPath("$[0].employees[0].companyId").value("1"))
        ;
    }

    @Test
    void should_get_company_when_perform_getById_given_company_and_id() throws Exception {
        //given
        Company company1 = new Company(1, "Spring");
        Company company2 = new Company(2, "Spring2");

        companyRepository.create(company1);
        companyRepository.create(company2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}", company1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Spring"))
                .andExpect(jsonPath("$.employees[0].id").isNumber())
                .andExpect(jsonPath("$.employees[0].name").value("Marcus"))
                .andExpect(jsonPath("$.employees[0].gender").value("Male"))
                .andExpect(jsonPath("$.employees[0].salary").value("298912220"))
                .andExpect(jsonPath("$.employees[0].companyId").value("1"));
    }

    @Test
    void should_get_all_employee_under_company_when_obtain_employee_list_given_employees_and_company() throws Exception {
        //given
        Company company1 = new Company(1, "Spring");

        companyRepository.findEmployeeById(1);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", company1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Marcus"))
                .andExpect(jsonPath("$[0].age").value("22"))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].salary").value("298912220"))
                .andExpect(jsonPath("$[0].companyId").value("1"));
    }

    @Test
    void should_get_all_companies_when_getByPaging_given_page_and_pageSize_and_company() throws Exception {
        Company company1 = new Company(1, "Spring");
        Company company2 = new Company(2, "Spring2");
        Company company3 = new Company(3, "Spring3");

        companyRepository.create(company1);
        companyRepository.create(company2);
        companyRepository.create(company3);

        String page = "1";
        String pageSize = "2";

        mockMvc.perform(MockMvcRequestBuilders.get("/companies?page=" + page + "&pageSize=" + pageSize))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Spring"))
                .andExpect(jsonPath("$[0].employees", hasSize(7)))
                .andExpect(jsonPath("$[0].employees[0].id").isNumber())
                .andExpect(jsonPath("$[0].employees[0].name").value("Marcus"))
                .andExpect(jsonPath("$[0].employees[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].employees[0].salary").value("298912220"))
                .andExpect(jsonPath("$[0].employees[0].companyId").value("1"))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("Spring2"))
                .andExpect(jsonPath("$[1].employees", hasSize(0)));
    }

    @Test
    void should_return_company_when_perform_post_given_company() throws Exception {
        //given
        String company = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"OOCL\"\n" +
                "}";

        //when
        //then
        mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(company))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("OOCL"))
                .andExpect(jsonPath("$.employees", hasSize(7)))
                .andExpect(jsonPath("$.employees[0].id").isNumber())
                .andExpect(jsonPath("$.employees[0].age").value("22"))
                .andExpect(jsonPath("$.employees[0].name").value("Marcus"))
                .andExpect(jsonPath("$.employees[0].gender").value("Male"))
                .andExpect(jsonPath("$.employees[0].salary").value("298912220"));
    }

    @Test
    void should_return_changed_company_when_perform_put_given_company_id() throws Exception {
        //given
        Company company1 = new Company(1, "Spring");
        Company company2 = new Company(2, "Spring2");
        Company company3 = new Company(3, "Spring3");

        companyRepository.create(company1);
        companyRepository.create(company2);
        companyRepository.create(company3);

        String company = "{\n" +
                "    \"name\": \"OOCL\"\n" +
                "}";

        //when
        //then
        mockMvc.perform(put("/companies/{id}", company1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(company))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("OOCL"));
    }

    @Test
    void should_delete_company_when_perform_delete_given_company_and_id() throws Exception {
        //given
        Company company1 = new Company(1, "Spring");
        Company company2 = new Company(2, "Spring2");
        Company company3 = new Company(3, "Spring3");

        companyRepository.create(company1);
        companyRepository.create(company2);
        companyRepository.create(company3);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", company1.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}