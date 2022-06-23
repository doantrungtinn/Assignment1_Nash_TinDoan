package com.tindoan.tshop.controller;

//import Service.productService;
//import Service.productService;
import com.tindoan.tshop.model.product;
import com.tindoan.tshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class productController {

    @Autowired
    private ProductRepository productrepository;

    @GetMapping
    public List<product> getAllproduct(){
        return this.productrepository.findAll();
    }



//    public productController(){
//    }


//    public String getStockItem(){
//        return "It's not working...!";
//    }

}
