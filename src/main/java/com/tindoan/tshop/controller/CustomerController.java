package com.tindoan.tshop.controller;


import com.tindoan.tshop.model.customer;
import com.tindoan.tshop.model.user;
import com.tindoan.tshop.repository.CustomerRepository;
import com.tindoan.tshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<customer> getAllcustomer(){
        return this.customerRepository.findAll();
    }
}
