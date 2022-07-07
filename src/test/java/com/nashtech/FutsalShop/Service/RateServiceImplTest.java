package com.nashtech.FutsalShop.Service;

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

import com.nashtech.FutsalShop.DTO.RateDTO;
import com.nashtech.FutsalShop.model.Categories;
import com.nashtech.FutsalShop.model.Person;
import com.nashtech.FutsalShop.model.Product;
import com.nashtech.FutsalShop.model.Rate;
import com.nashtech.FutsalShop.exception.ObjectAlreadyExistException;
import com.nashtech.FutsalShop.exception.ObjectNotFoundException;
import com.nashtech.FutsalShop.repository.PersonRepository;
import com.nashtech.FutsalShop.repository.ProductRepository;
import com.nashtech.FutsalShop.repository.RateRepository;
import com.nashtech.FutsalShop.services.OrderService;
import com.nashtech.FutsalShop.services.RateService;
import com.nashtech.FutsalShop.services.impl.RateServiceImpl;

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
	private Rate rate1;
	private Rate rate2;
	private Rate rate3;
	private RateDTO rateDTO;
	private Person person1;
	private Product prod1;
	List<Rate> rateList;

	@BeforeEach
	public void setup() {
//		rateKey = new RateKey(0, "prodZ");
//		rate1 = new RateEntity(new RateKey(1, "prodA"), 1, "BAD");
//		rate2 = new RateEntity(new RateKey(2, "prodB"), 3, "MEDIUM");
//		rate3 = new RateEntity(new RateKey(3, "prodC"), 5, "GOOD");
		rate1 = new Rate(1, 1, "BAD");
		rate2 = new Rate(2, 3, "MEDIUM");
		rate3 = new Rate(3, 5, "GOOD");
		rateDTO = new RateDTO("ProdA", 1, 3, "MEDIUM");
		rateList = new ArrayList<Rate>(List.of(rate1, rate2, rate3));
		person1 = new Person(1, "doantrungtinn@gmail.com", "123456", "Doan Trung Tin", "ADMIN");
		prod1 = new Product("ProdA", "Product A", (float) 3.45, 2,
				new Categories(1, "Cate 1", "This is categories number 1", true));
		person1.setReviews(new HashSet<Rate>(rateList));
		prod1.setReviews(new HashSet<Rate>(rateList));
		rate1.setProduct(prod1);
		rate1.setCustomer(person1);
	}

	@Test
	public void createRateSuccess_Test() {
		when(rateRepo.existsById(1)).thenReturn(false);
		when(personRepo.findById(person1.getId())).thenReturn(Optional.of(person1));
		when(rateRepo.save(Mockito.any(Rate.class))).thenReturn(rate1);
		
		Rate rate_test = rateService.createRate(rateDTO);
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
		when(prodRepo.save(Mockito.any(Product.class))).thenReturn(prod1);
		when(personRepo.save(Mockito.any(Person.class))).thenReturn(person1);
		
		doNothing().when(rateRepo).delete(Mockito.any(Rate.class));
		assertTrue(rateService.deleteRate(rate1.getId(), String.valueOf(person1.getId())));
	}

}
