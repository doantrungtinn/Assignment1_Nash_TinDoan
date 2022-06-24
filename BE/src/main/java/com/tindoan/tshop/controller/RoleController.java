package com.tindoan.tshop.controller;


import com.tindoan.tshop.model.role;

import com.tindoan.tshop.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<role> getAllrole() {
        return this.roleRepository.findAll();
    }
}
