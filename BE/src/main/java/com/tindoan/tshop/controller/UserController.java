package com.tindoan.tshop.controller;


import com.tindoan.tshop.model.user;
import com.tindoan.tshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<user> getAlluser(){
        return this.userRepository.findAll();
    }
}
