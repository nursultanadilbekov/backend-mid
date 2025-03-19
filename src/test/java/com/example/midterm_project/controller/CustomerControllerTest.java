package com.example.midterm_project.controller;

import com.example.midterm_project.dto.customer.CustomerRequest;
import com.example.midterm_project.dto.customer.CustomerResponse;
import com.example.midterm_project.service.interfaces.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldFindCustomerById() throws Exception {
        Long id = 1L;
        String token = "Bearer mock-token";
        CustomerResponse response = new CustomerResponse();
        response.setId(id);
        response.setName("John Doe");

        when(customerService.find(id, token)).thenReturn(response);

        mockMvc.perform(get("/customer/find/{id}", id)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void shouldDeleteCustomerSuccessfully() throws Exception {
        Long id = 1L;

        doNothing().when(customerService).delete(id);

        mockMvc.perform(delete("/customer/delete")
                        .param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void shouldGetAllCustomers() throws Exception {
        CustomerResponse customer1 = new CustomerResponse();
        customer1.setId(1L);
        customer1.setName("John Doe");
        CustomerResponse customer2 = new CustomerResponse();
        customer2.setId(2L);
        customer2.setName("Jane Smith");
        List<CustomerResponse> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAll()).thenReturn(customers);

        mockMvc.perform(get("/customer/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customers)));
    }

    @Test
    void shouldUpdateCustomerSuccessfully() throws Exception {
        Long id = 1L;
        CustomerRequest request = new CustomerRequest();
        request.setName("John Updated");

        doNothing().when(customerService).update(eq(id), any(CustomerRequest.class));

        mockMvc.perform(post("/customer/update")
                        .param("id", id.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}