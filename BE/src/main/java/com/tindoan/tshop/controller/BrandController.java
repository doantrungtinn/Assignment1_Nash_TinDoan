package com.tindoan.tshop.controller;


import com.tindoan.tshop.model.brand;
import com.tindoan.tshop.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping
    public List<brand> getAllbrand(){
        return this.brandRepository.findAll();
    }
}
