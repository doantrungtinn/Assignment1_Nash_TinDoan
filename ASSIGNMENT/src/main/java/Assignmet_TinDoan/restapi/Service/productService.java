package Assignmet_TinDoan.restapi.Service;


import Assignmet_TinDoan.restapi.Models.product;
import Assignmet_TinDoan.restapi.Repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productService {
    @Autowired
        productRepository productRepository;
    //CREATE
    public product createProduct(product product){
        return productRepository.save(product);
    }

    //READ
    public List<product> getProduct(){
        return productRepository.findAll();
    }

    //DELETE

    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }

    //update

//    public product updateProduct(Long productId,product  productDetails){
//        product product = productRepository.findById(productId).get();
//
//    }
}
