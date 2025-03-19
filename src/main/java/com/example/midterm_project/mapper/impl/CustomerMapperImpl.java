package com.example.midterm_project.mapper.impl;

import com.example.midterm_project.dto.cars.CarsResponse;
import com.example.midterm_project.dto.customer.CustomerResponse;
import com.example.midterm_project.entities.Car;
import com.example.midterm_project.entities.Customer;
import com.example.midterm_project.mapper.interfaces.CustomerMapper;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerResponse toDto(Customer customer) {
        CustomerResponse customerResponse=new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setName(customer.getName());

        return customerResponse;
    }

    @Override
    public List<CustomerResponse> toDtos(List<Customer> all) {
        List<CustomerResponse>customerResponses=new ArrayList<>();
        for(Customer customer:all){
            customerResponses.add(toDto(customer));
        }
        return customerResponses;
    }
}
