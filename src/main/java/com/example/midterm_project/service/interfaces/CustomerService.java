package com.example.midterm_project.service.interfaces;

import com.example.midterm_project.dto.customer.CustomerRequest;
import com.example.midterm_project.dto.customer.CustomerResponse;
import com.example.midterm_project.dto.repairer.RepairerRequest;

import java.util.List;

public interface CustomerService {
    CustomerResponse find(Long id, String token);

    void delete(Long id);

    List<CustomerResponse> getAll();

    void update(Long id, CustomerRequest customerRequest);
}
