package com.tindoan.tshop.service;



import com.tindoan.tshop.model.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class ProductService {
//    @Autowired
//    Repository.ProductRepository ProductRepository;
//    //CREATE
//    public product createProduct(product product){
//        return ProductRepository.save(product);
//    }
//
//    //READ
//    public List<product> getProduct(){
//        return ProductRepository.findAll();
//    }
//
//    //DELETE
//
//    public void deleteProduct(Long productId){
//        ProductRepository.deleteById(productId);
//    }
//
//    //update
//
////    public product updateProduct(Long productId,product  productDetails){
////        product product = productRepository.findById(productId).get();
////
////    }
//}
