package Assignmet_TinDoan.restapi;

import Assignmet_TinDoan.restapi.Controller.productController;
import Assignmet_TinDoan.restapi.Controller.ratingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {productController.class, ratingController.class})
public class RestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

}
