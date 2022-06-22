package Assignmet_TinDoan.restapi.Controller;

//import Service.productService;
//import Service.productService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class productController {
    public productController(){
    }

    @RequestMapping("/product")
    public String getStockItem(){
        return "It's not working...!";
    }

}
