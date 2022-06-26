package com.nashtech.MyBikeShop.Service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.nashtech.MyBikeShop.model.categories;
import com.nashtech.MyBikeShop.model.orderdetail;
import com.nashtech.MyBikeShop.model.order;
import com.nashtech.MyBikeShop.model.person;
import com.nashtech.MyBikeShop.model.product;
import com.nashtech.MyBikeShop.model.orderdetail.OrderDetailsKey;
import com.nashtech.MyBikeShop.repository.OrderDetailRepository;
import com.nashtech.MyBikeShop.services.OrderDetailService;
import com.nashtech.MyBikeShop.services.OrderService;
import com.nashtech.MyBikeShop.services.ProductService;
import com.nashtech.MyBikeShop.services.impl.OrderDetailServiceImpl;

@SpringBootTest
public class OrderDetailServiceImplTest {
	@InjectMocks
	OrderDetailService detailService = new OrderDetailServiceImpl();

	@Mock
	OrderDetailRepository detailRepo;

	@Mock
	OrderService orderService;

	@Mock
	ProductService productService;

	private orderdetail detail1;
	private orderdetail detail2;
	private orderdetail detail3;
	private order order1;
	private categories cate1;
	private product prod1;
	List<orderdetail> listDetail;
	private final int list_size = 3;

	@BeforeEach
	public void setup() {
		detail1 = new orderdetail(new OrderDetailsKey(1, "ProdA"), 1, 100.0);
		detail2 = new orderdetail(new OrderDetailsKey(1, "ProdB"), 2, 200.0);
		detail3 = new orderdetail(new OrderDetailsKey(1, "ProdC"), 3, 300.0);
		order1 = new order(1, "Quan 1", 1,
				new person(1, "lqhuy2309@gmail.com", "123456", "Quang Huy", "ADMIN"));
		cate1 = new categories(1, "Cate 1", "This is categories number 1", true);
		prod1 = new product("ProdA", "Product A", (float) 3.45, 2, cate1);
		listDetail = new ArrayList<orderdetail>(Arrays.asList(detail1, detail2, detail3));
	}

	@Test
	public void retrieveOrdersSuccess_Test() {
		when(detailRepo.findAll()).thenReturn(listDetail);
		assertEquals(list_size, detailService.retrieveOrders().size());
	}

	@Test
	public void getDetailOrderByOrderIdSuccess_Test() {
		when(detailRepo.findByIdOrderId(Mockito.anyInt())).thenReturn(new HashSet<orderdetail>() {
			{
				add(detail1);
				add(detail2);
				add(detail3);
			}
		});
		assertEquals(list_size, detailService.getDetailOrderByOrderId(detail1.getId().getOrderId()).size());
	}

	@Test
	public void createDetailSuccess_Test() {
		when(productService.updateProductQuantity(Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
		when(detailRepo.save(detail1)).thenReturn(detail1);
		assertTrue(detailService.createDetail(detail1));
	}

	@Test
	public void createDetailFailUpdateProdQuantity_Test() {
		when(productService.updateProductQuantity(Mockito.anyString(), Mockito.anyInt())).thenReturn(false);
		assertFalse(detailService.createDetail(detail1));
	}

	@Test
	public void deleteDetailSuccess_Test() {
		when(orderService.getOrder(detail1.getId().getOrderId())).thenReturn(Optional.of(order1));
		when(productService.updateProductQuantity(detail1.getId().getProductId(), detail1.getAmmount()))
				.thenReturn(true);
		doNothing().when(detailRepo).delete(detail1);
		assertTrue(detailService.deleteDetail(detail1));
	}

	@Test
	public void deleteDetailFailUpdateProdQuantity_Test() {
		when(orderService.getOrder(detail1.getId().getOrderId())).thenReturn(Optional.of(order1));
		when(productService.updateProductQuantity(detail1.getId().getProductId(), detail1.getAmmount()))
				.thenReturn(false);
		assertFalse(detailService.deleteDetail(detail1));
	}

	@Test
	public void updateDetailCancelSuccess_Test() {
		when(productService.updateProductQuantityToCancel(detail1.getId().getProductId(), detail1.getAmmount()))
				.thenReturn(true);
		assertTrue(detailService.updateDetailCancel(detail1));
	}

	@Test
	public void updateDetailCancelFailUpdateProdQuantity_Test() {
		when(productService.updateProductQuantity(detail1.getId().getProductId(), detail1.getAmmount()))
				.thenReturn(false);
		assertFalse(detailService.updateDetailCancel(detail1));
	}

	@Test
	public void updateDetailSuccess_Test() {
		when(productService.getProduct(detail1.getId().getProductId())).thenReturn(Optional.of(prod1));
		when(productService.updateProductQuantity(Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
		assertTrue(detailService.updateDetail(detail1));
	}

	@Test
	public void updateDetailFailUpdateProdQuantity_Test() {
		when(productService.getProduct(detail1.getId().getProductId())).thenReturn(Optional.of(prod1));
		when(productService.updateProductQuantity(detail1.getId().getProductId(), detail1.getAmmount()))
				.thenReturn(false);
		assertFalse(detailService.updateDetail(detail1));
	}
}
