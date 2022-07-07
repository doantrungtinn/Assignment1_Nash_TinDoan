package com.nashtech.FutsalShop.Controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nashtech.FutsalShop.DTO.ProductDTO;
import com.nashtech.FutsalShop.controller.ProductController;
import com.nashtech.FutsalShop.model.Categories;
import com.nashtech.FutsalShop.model.Person;
import com.nashtech.FutsalShop.model.Product;
import com.nashtech.FutsalShop.services.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService prodService;

	@InjectMocks
	ProductController prodController;

	@Test
	@WithMockUser(username = "admin", password = "123456", roles = "ADMIN") 
	public void testCreateProd() throws Exception{
		Categories cate = new Categories(1, "cate1", "cateDes",true);
		Product prod = new Product("a1","PRODUCT ENTITY",(float)6.5,4,cate);
		ProductDTO prodDTO = new ProductDTO("a1DTO","PRODUCT DTO",(float)6.5,4,1,"A");
		Person person1= new Person(1,"doantrungtinn@gmail.com","123456","Doan Trung Tin","ADMIN");
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		Mockito.when(prodService.createProduct(Mockito.anyObject(),person1.getId())).thenReturn(prod);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
				.content(ow.writeValueAsString(prodDTO))
		        .contentType(MediaType.APPLICATION_JSON)
		        .characterEncoding("UTF-8")
		        .accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.equalTo("a1")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.equalTo("PRODUCT ENTITY")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.quantity",Matchers.equalTo(4)));
	}
}
