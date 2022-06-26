package com.nashtech.MyBikeShop.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.nashtech.MyBikeShop.DTO.OrderImportDTO;
import com.nashtech.MyBikeShop.model.categories;
import com.nashtech.MyBikeShop.model.orderimportdetail;
import com.nashtech.MyBikeShop.model.orderimportdetail.OrderImportDetailsKey;
import com.nashtech.MyBikeShop.repository.OrderImportRepository;
import com.nashtech.MyBikeShop.model.orderimport;
import com.nashtech.MyBikeShop.model.person;
import com.nashtech.MyBikeShop.model.product;
import com.nashtech.MyBikeShop.services.OrderImportDetailService;
import com.nashtech.MyBikeShop.services.OrderImportService;
import com.nashtech.MyBikeShop.services.PersonService;
import com.nashtech.MyBikeShop.services.ProductService;
import com.nashtech.MyBikeShop.services.impl.OrderImportServiceImpl;

@SpringBootTest
public class OrderImportServiceImplTest {
	@InjectMocks
	OrderImportService importService = new OrderImportServiceImpl();

	@Mock
	ProductService productService;

	@Mock
	ModelMapper mapper;

	@Mock
	PersonService personService;

	@Mock
	OrderImportDetailService importDetailService;

	@Mock
	OrderImportRepository orderImportRepo;

	private orderimport import1;
	private orderimport import2;
	private orderimport import3;
	private OrderImportDTO import1DTO;
	private orderimportdetail detail1;
	private orderimportdetail detail2;
	private orderimportdetail detail3;
	private person person1;
	private product prod1;
	private List<orderimport> listImport;
	private Set<orderimportdetail> listDetail;

	@BeforeEach
	public void setup() {
		detail1 = new orderimportdetail(new OrderImportDetailsKey(1, "ProdA"), 1, (float) 1.0);
		detail2 = new orderimportdetail(new OrderImportDetailsKey(1, "ProdB"), 2, (float) 2.0);
		detail3 = new orderimportdetail(new OrderImportDetailsKey(1, "ProdC"), 3, (float) 3.0);
		person1 = new person(1, "lqhuy2309@gmail.com", "123456", "Quang Huy", "ADMIN");
		prod1 = new product("ProdA", "Product A", (float) 3.45, 2,
				new categories(1, "Cate 1", "This is categories number 1", true));

		listDetail = Stream.of(detail1, detail2, detail3).collect(Collectors.toCollection(HashSet::new));
		import1 = new orderimport(1, true, person1, listDetail);
		import2 = new orderimport(2, true, person1, listDetail);
		import3 = new orderimport(3, true, person1, listDetail);
		import1DTO = new OrderImportDTO();
		listImport = new ArrayList<orderimport>(List.of(import1, import2, import3));
		person1.setOrdersImport(new HashSet<orderimport>(listImport));
	}

	@Test
	public void findOrderImportByIdSuccess_Test() {
		when(orderImportRepo.findById(import1.getId())).thenReturn(Optional.of(import1));
		assertEquals(import1, importService.findOrderImportById(import1.getId()));
	}

	@Test
	public void createOrderImportSuccess_Test() {
		when(personService.getPerson(Mockito.anyInt())).thenReturn(Optional.of(person1));
		when(productService.getProduct(Mockito.anyString())).thenReturn(Optional.of(prod1));
		when(productService.updateProductWithoutCheckAnything(prod1)).thenReturn(prod1);
		when(orderImportRepo.save(import1)).thenReturn(import1);
		assertEquals(import1, importService.createOrderImport(import1, person1.getId()));
	}

	@Test
	public void updateOrderImportSuccess_Test() {
		when(productService.getProduct(Mockito.anyString())).thenReturn(Optional.of(prod1));
		when(productService.updateProductWithoutCheckAnything(prod1)).thenReturn(prod1);
		when(orderImportRepo.findById(import1.getId())).thenReturn(Optional.of(import1));
		when(orderImportRepo.save(import1)).thenReturn(import1);
		assertEquals(import1, importService.updateOrderImport(import1DTO, import1.getId(), person1.getId()));
	}

	@Test
	public void deleteOrderImportSuccess_Test() {
		when(productService.updateProductWithoutCheckAnything(prod1)).thenReturn(prod1);
		when(personService.getPerson(Mockito.anyInt())).thenReturn(Optional.of(person1));
		when(orderImportRepo.findById(import1.getId())).thenReturn(Optional.of(import1));
		when(orderImportRepo.save(import1)).thenReturn(import1);

		assertTrue(importService.deleteOrderImport(import1.getId()));
	}
}
