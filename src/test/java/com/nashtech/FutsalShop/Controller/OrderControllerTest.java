package com.nashtech.FutsalShop.Controller;

import java.util.List;
import java.util.Arrays;

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
import com.nashtech.FutsalShop.DTO.OrderDTO;
import com.nashtech.FutsalShop.DTO.PersonDTO;
import com.nashtech.FutsalShop.controller.OrderController;
import com.nashtech.FutsalShop.model.order;
import com.nashtech.FutsalShop.model.person;
import com.nashtech.FutsalShop.services.OrderService;


//@WebMvcTest(OrderController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	@Test
	@WithMockUser(username = "admin", password = "123456", roles = "ADMIN")
	public void testGetOrder() throws Exception {
		person customers = new person(1,"doantrungtinn@gmail.com","123456","A","ADMIN");
		order order1 = new order(1,  "A", 1, customers);
		order order2 = new order(2, "B", 1, customers);
		order order3 = new order(3,  "C", 1, customers);
		List<order> listOrder = Arrays.asList(order1,order2,order3);

		Mockito.when(orderService.getOrderPage(Mockito.anyInt(), Mockito.anyInt()))
											.thenReturn(listOrder);
				mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order?pagenum=4&size=4"))
				//.contentType(MediaType.APPLICATION_JSON) //cmt
				//.characterEncoding("UTF-8") //cmt
		        //.accept(MediaType.APPLICATION_JSON)) //cmt
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)));
//				.andReturn();
//		String resultDOW = result.getResponse().getContentAsString(); //cmt
//		System.out.println("KET QUA: " +resultDOW); //cmt
	}

	@Test
	@WithMockUser(roles = "USER")
	public void createOrderTest() throws Exception {
		PersonDTO customers = new PersonDTO(1,"doantrungtinn@gmail.com","123456","A","ADMIN", true);
		OrderDTO orderDTO = new OrderDTO(  "ABC", 1,"doantrungtinn@gmail.com");
		order order = new order(1,  "ABC", 1, new person(customers));
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

		Mockito.when(orderService.createOrder(Mockito.anyObject())).thenReturn(order);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order")
				.content(ow.writeValueAsString(orderDTO))
		        .contentType(MediaType.APPLICATION_JSON)
		        .characterEncoding("UTF-8")
		        .accept(MediaType.APPLICATION_JSON))
//				.andReturn().getResponse().getContentAsString()); //cmt
				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)) //cmt
//				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1))); //cmt
		.andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.equalTo(1)));
//		.andReturn();
//		System.out.println("KET QUA: "+ result.getResponse().getContentAsString());//cmt
//			.andExpect(MockMvcResultMatchers.model().attribute("id",1)); //cmt
	}
}
