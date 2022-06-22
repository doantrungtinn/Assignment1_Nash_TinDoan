package Controller;

//import Service.productService;
//import Service.productService;
import Models.product;
import Repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/product"}, method = {RequestMethod.GET})

public class productController {
    @Autowired
    private productRepository repository;

    public List<product> findAll(){
        return  findAll();
    }

}
