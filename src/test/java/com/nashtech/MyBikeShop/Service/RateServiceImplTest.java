package com.nashtech.MyBikeShop.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.nashtech.MyBikeShop.DTO.RateDTO;
import com.nashtech.MyBikeShop.model.categories;
import com.nashtech.MyBikeShop.model.person;
import com.nashtech.MyBikeShop.model.product;
import com.nashtech.MyBikeShop.model.rate;
import com.nashtech.MyBikeShop.exception.ObjectAlreadyExistException;
import com.nashtech.MyBikeShop.exception.ObjectNotFoundException;
import com.nashtech.MyBikeShop.repository.PersonRepository;
import com.nashtech.MyBikeShop.repository.ProductRepository;
import com.nashtech.MyBikeShop.repository.RateRepository;
import com.nashtech.MyBikeShop.services.OrderService;
import com.nashtech.MyBikeShop.services.RateService;
import com.nashtech.MyBikeShop.services.impl.RateServiceImpl;

@SpringBootTest
public class RateServiceImplTest {
	@InjectMocks
	RateService rateService = new RateServiceImpl();

	@Mock
	PersonRepository personRepo;

	@Mock
	ProductRepository prodRepo;

	@Mock
	OrderService orderService;

	@Mock
	RateRepository rateRepo;

//	private RateKey rateKey;
	private rate rate1;
	private rate rate2;
	private rate rate3;
	private RateDTO rateDTO;
	private person person1;
	private product prod1;
	List<rate> rateList;

	@BeforeEach
	public void setup() {
//		rateKey = new RateKey(0, "prodZ");
//		rate1 = new RateEntity(new RateKey(1, "prodA"), 1, "BAD");
//		rate2 = new RateEntity(new RateKey(2, "prodB"), 3, "MEDIUM");
//		rate3 = new RateEntity(new RateKey(3, "prodC"), 5, "GOOD");
		rate1 = new rate(1, 1, "BAD");
		rate2 = new rate(2, 3, "MEDIUM");
		rate3 = new rate(3, 5, "GOOD");
		rateDTO = new RateDTO("ProdA", 1, 3, "MEDIUM");
		rateList = new ArrayList<rate>(List.of(rate1, rate2, rate3));
		person1 = new person(1, "lqhuy2309@gmail.com", "123456", "Quang Huy", "ADMIN");
		prod1 = new product("ProdA", "Product A", (float) 3.45, 2,
				new categories(1, "Cate 1", "This is categories number 1", true));
		person1.setReviews(new HashSet<rate>(rateList));
		prod1.setReviews(new HashSet<rate>(rateList));
		rate1.setProduct(prod1);
		rate1.setCustomer(person1);
	}

	@Test
	public void createRateSuccess_Test() {
		when(rateRepo.existsById(1)).thenReturn(false);
		when(personRepo.findById(person1.getId())).thenReturn(Optional.of(person1));
		when(rateRepo.save(Mockito.any(rate.class))).thenReturn(rate1);
		
		rate rate_test = rateService.createRate(rateDTO);
		assertEquals(rate1, rate_test);
	}
	
	@Test
	public void createRateFailedExistById_Test() {
		when(rateRepo.existsByProductIdAndCustomerId(Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
		when(personRepo.findById(person1.getId())).thenReturn(Optional.of(person1));
		assertThrows(ObjectAlreadyExistException.class,
				() -> rateService.createRate(rateDTO));
	}
	
	@Test
	public void createRateFailedUserNotFound_Test() {
		when(rateRepo.existsByProductIdAndCustomerId(Mockito.anyString(), Mockito.anyInt())).thenReturn(false);
		assertThrows(ObjectNotFoundException.class,
				() -> rateService.createRate(rateDTO));
	}

	@Test
	public void deleteRateSuccess_Test() {
		when(prodRepo.findByIdIgnoreCase(Mockito.anyString())).thenReturn(Optional.of(prod1));
		when(personRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(person1));
		when(rateRepo.findById(rate1.getId())).thenReturn(Optional.of(rate1));
		when(prodRepo.save(Mockito.any(product.class))).thenReturn(prod1);
		when(personRepo.save(Mockito.any(person.class))).thenReturn(person1);
		
		doNothing().when(rateRepo).delete(Mockito.any(rate.class));
		assertTrue(rateService.deleteRate(rate1.getId(), String.valueOf(person1.getId())));
	}

}
