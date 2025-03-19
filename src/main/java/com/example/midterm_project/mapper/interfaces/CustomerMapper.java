package com.example.midterm_project.mapper.interfaces;

import com.example.midterm_project.dto.customer.CustomerResponse;
import com.example.midterm_project.dto.repairer.RepairerResponse;
import com.example.midterm_project.entities.Customer;
import com.example.midterm_project.entities.Repairer;

import java.util.List;

public interface CustomerMapper {

    CustomerResponse toDto(Customer customer);

    List<CustomerResponse> toDtos(List<Customer>all);
}
