package com.example.midterm_project.controller;

import com.example.midterm_project.dto.customer.CustomerRequest;
import com.example.midterm_project.dto.customer.CustomerResponse;
import com.example.midterm_project.dto.repairer.RepairerRequest;
import com.example.midterm_project.dto.repairer.RepairerResponse;
import com.example.midterm_project.service.interfaces.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    CustomerService customerService;


    @GetMapping("/find/{id}")
    public CustomerResponse find(@PathVariable Long id, @RequestHeader("Authorization") String token){
        return customerService.find(id,token);
    }

    @DeleteMapping("/delete")
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }

    @GetMapping("/getAll")
    public List<CustomerResponse> customerResponses(){
        return customerService.getAll();
    }

    @PostMapping("/update")
    public void update(@PathVariable Long id, @RequestBody CustomerRequest customerRequest){
        customerService.update(id,customerRequest);
    }
}
