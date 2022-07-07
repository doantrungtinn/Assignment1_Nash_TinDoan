package com.nashtech.FutsalShop.Service;

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

import com.nashtech.FutsalShop.model.Categories;
import com.nashtech.FutsalShop.model.Orderdetail;
import com.nashtech.FutsalShop.model.Order;
import com.nashtech.FutsalShop.model.Person;
import com.nashtech.FutsalShop.model.Product;
import com.nashtech.FutsalShop.model.Orderdetail.OrderDetailsKey;
import com.nashtech.FutsalShop.repository.OrderDetailRepository;
import com.nashtech.FutsalShop.services.OrderDetailService;
import com.nashtech.FutsalShop.services.OrderService;
import com.nashtech.FutsalShop.services.ProductService;
import com.nashtech.FutsalShop.services.impl.OrderDetailServiceImpl;

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

	private Orderdetail detail1;
	private Orderdetail detail2;
	private Orderdetail detail3;
	private Order order1;
	private Categories cate1;
	private Product prod1;
	List<Orderdetail> listDetail;
	private final int list_size = 3;

	@BeforeEach
	public void setup() {
		detail1 = new Orderdetail(new OrderDetailsKey(1, "ProdA"), 1, 100.0);
		detail2 = new Orderdetail(new OrderDetailsKey(1, "ProdB"), 2, 200.0);
		detail3 = new Orderdetail(new OrderDetailsKey(1, "ProdC"), 3, 300.0);
		order1 = new Order(1, "Quan 1", 1,
				new Person(1, "doantrungtinn@gmail.com", "123456", "Trung Tin", "ADMIN"));
		cate1 = new Categories(1, "Cate 1", "This is categories number 1", true);
		prod1 = new Product("ProdA", "Product A", (float) 3.45, 2, cate1);
		listDetail = new ArrayList<Orderdetail>(Arrays.asList(detail1, detail2, detail3));
	}

	@Test
	public void retrieveOrdersSuccess_Test() {
		when(detailRepo.findAll()).thenReturn(listDetail);
		assertEquals(list_size, detailService.retrieveOrders().size());
	}

	@Test
	public void getDetailOrderByOrderIdSuccess_Test() {
		when(detailRepo.findByIdOrderId(Mockito.anyInt())).thenReturn(new HashSet<Orderdetail>() {
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
