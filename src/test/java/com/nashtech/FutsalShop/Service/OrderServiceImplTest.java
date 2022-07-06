package com.nashtech.FutsalShop.Service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.nashtech.FutsalShop.DTO.OrderDTO;
import com.nashtech.FutsalShop.DTO.OrderDetailDTO;
import com.nashtech.FutsalShop.model.categories;
import com.nashtech.FutsalShop.model.orderdetail;
import com.nashtech.FutsalShop.model.order;
import com.nashtech.FutsalShop.model.person;
import com.nashtech.FutsalShop.model.product;
import com.nashtech.FutsalShop.exception.ObjectPropertiesIllegalException;
import com.nashtech.FutsalShop.repository.OrderRepository;
import com.nashtech.FutsalShop.services.OrderDetailService;
import com.nashtech.FutsalShop.services.OrderService;
import com.nashtech.FutsalShop.services.PersonService;
import com.nashtech.FutsalShop.services.ProductService;
import com.nashtech.FutsalShop.services.impl.OrderServiceImpl;

@SpringBootTest
public class OrderServiceImplTest {
	@InjectMocks
	OrderService orderService = new OrderServiceImpl();

	@Mock
	OrderRepository orderRepository;

	@Mock
	ProductService productService;

	@Mock
	ModelMapper mapper;

	@Mock
	PersonService personService;

	@Mock
	OrderDetailService orderDetailService;

	private order order1;
	private order order2;
	private order order3;
	private order order4;
	private orderdetail detail1;
	private OrderDetailDTO detail1DTO;
	private OrderDetailDTO detail2DTO;
	private OrderDTO order1DTO;
	private person person1;
	private categories cate1;
	private product prod1;
	List<order> listOrder;
	List<OrderDetailDTO> listDetailDTO;
	private final int list_size = 4;

	@BeforeEach
	public void setup() {
		person1 = new person(1, "doantrungtinn@gmail.com", "123456", "Doan Trung Tin", "ADMIN");
		order1 = new order(1, "Quan 1", 1, person1);
		order2 = new order(2, "Quan 2", 2, person1);
		order3 = new order(3, "Quan 3", 3, person1);
		order4 = new order(4, "Quan 4", 4, person1);
		detail1DTO = new OrderDetailDTO(1, "ProdA", 11, 1000.0);
		detail2DTO = new OrderDetailDTO(2, "ProdB", 22, 2000.0);

		cate1 = new categories(1, "Cate 1", "This is categories number 1", true);
		prod1 = new product("ProdA", "Product A", (float) 3.45, 2, cate1);
		listDetailDTO = new ArrayList<OrderDetailDTO>(List.of(detail1DTO, detail2DTO));
		listOrder = new ArrayList<order>(List.of(order1, order2, order3, order4));
		order1DTO = new OrderDTO(1, "Quan 1", 1, person1.getEmail(), listDetailDTO);
		person1.setOrders(new HashSet<order>(listOrder));
	}

	@Test
	public void retrieveOrdersSuccess_Test() {
		when(orderRepository.findAll()).thenReturn(listOrder);
		assertEquals(list_size, orderService.retrieveOrders().size());
	}

	@Test
	public void createOrderSuccess_Test() throws MessagingException {
		when(personService.getPerson(Mockito.anyString())).thenReturn(person1);
		when(orderRepository.save(Mockito.any(order.class))).thenReturn(order1);
		when(orderDetailService.createDetail(Mockito.any(orderdetail.class))).thenReturn(true);
		when(productService.getProduct(Mockito.anyString())).thenReturn(Optional.of(prod1));
		when(orderRepository.getById(Mockito.anyInt())).thenReturn(order1);
		JavaMailSender javaMailSender = spy(new JavaMailSenderImpl());
		MimeMessage mimeMessage = mock(MimeMessage.class);
		OrderService orderService_test = new OrderServiceImpl(orderRepository, productService, mapper, personService,
				orderDetailService, javaMailSender);
		doReturn(mimeMessage).when(javaMailSender).createMimeMessage();
		doNothing().when(javaMailSender).send(Mockito.any(MimeMessage.class));
		order order_test = orderService_test.createOrder(order1DTO);
		assertEquals(order1DTO.getId(), order_test.getId());
	}

	@Test
	public void createOrderFailedAddDetail_Test() {
		when(personService.getPerson(Mockito.anyString())).thenReturn(person1);
		when(orderRepository.save(Mockito.any(order.class))).thenReturn(order1);
		when(orderDetailService.createDetail(Mockito.any(orderdetail.class))).thenReturn(false);
		when(productService.getProduct(Mockito.anyString())).thenReturn(Optional.of(prod1));
		when(orderRepository.getById(Mockito.anyInt())).thenReturn(order1);
		assertThrows(ObjectPropertiesIllegalException.class, () -> orderService.createOrder(order1DTO));
	}

	@Test
	public void updateOrderSuccess_Test() {
		when(orderRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(order1));
		when(orderDetailService.getDetailOrderByOrderId(Mockito.anyInt())).thenReturn(new HashSet<orderdetail>() {
			{
				add(detail1);
			}
		});
		when(orderDetailService.deleteDetail(detail1)).thenReturn(true);
		when(orderDetailService.createDetail(Mockito.any(orderdetail.class))).thenReturn(true);
		when(orderRepository.save(Mockito.any(order.class))).thenReturn(order1);
		assertTrue(orderService.updateOrder(order1DTO));
	}

	@Test
	public void updateOrderFalseDeleteDetail_Test() {
		when(orderRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(order1));
		when(orderDetailService.getDetailOrderByOrderId(Mockito.anyInt())).thenReturn(new HashSet<orderdetail>() {
			{
				add(detail1);
			}
		});
		when(orderDetailService.deleteDetail(Mockito.any(orderdetail.class))).thenReturn(false);
		when(orderDetailService.createDetail(Mockito.any(orderdetail.class))).thenReturn(true);
		when(orderRepository.save(Mockito.any(order.class))).thenReturn(order1);
		assertFalse(orderService.updateOrder(order1DTO));
	}

	@Test
	public void deleteOrderSuccess_Test() {
		when(orderRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(order1));
		when(personService.getPerson(Mockito.anyInt())).thenReturn(Optional.of(person1));
		when(productService.updateProductQuantity(Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
		doNothing().when(orderRepository).delete(Mockito.any(order.class));
		assertTrue(orderService.deleteOrder(order1.getId()));
		assertFalse(person1.getOrders().contains(order1));

	}
}
