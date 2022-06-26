package com.nashtech.MyBikeShop.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import com.nashtech.MyBikeShop.DTO.ProductDTO;
import com.nashtech.MyBikeShop.model.categories;
import com.nashtech.MyBikeShop.model.person;
import com.nashtech.MyBikeShop.model.product;
import com.nashtech.MyBikeShop.exception.ObjectAlreadyExistException;
import com.nashtech.MyBikeShop.exception.ObjectNotFoundException;
import com.nashtech.MyBikeShop.exception.ObjectPropertiesIllegalException;
import com.nashtech.MyBikeShop.repository.ProductRepository;
import com.nashtech.MyBikeShop.services.CategoriesService;
import com.nashtech.MyBikeShop.services.PersonService;
import com.nashtech.MyBikeShop.services.ProductService;
import com.nashtech.MyBikeShop.services.impl.ProductServiceImpl;

@SpringBootTest
public class ProductServiceImplTest {

	@InjectMocks
	ProductService prodService = new ProductServiceImpl();

	@Mock
	CategoriesService cateService;

	@Mock
	PersonService personService;

	@Mock
	ProductRepository prodRepository;

	private categories cate1;
	private product prod1;
	private product prod2;
	private product prod3;
	private ProductDTO prodDTO;
	private person person1;
	List<product> prodList;
	private final int list_size = 3;

	@BeforeEach
	public void setup() {
		cate1 = new categories(1, "Cate 1", "This is categories number 1", true);
		prod1 = new product("ProdA", "Product A", (float) 3.45, 2, cate1);
		prod2 = new product("ProdB", "Product B", (float) 3.45, 2, cate1);
		prod3 = new product("ProdC", "Product C", (float) 3.45, 5, cate1);
		prodList = new ArrayList<product>(List.of(prod1, prod2, prod3));
		prodDTO = new ProductDTO("ProdA", "Product A", (float) 3.45, 3, 1, "lqhuy2309@gmail.com");
		person1 = new person(1, "lqhuy2309@gmail.com", "123456", "Quang Huy", "ADMIN");
	}

	@Test
	public void retrieveProductsSuccess_Test() {
		Sort sortable = Sort.by("id").ascending();
		when(prodRepository.findByStatusNotAndCategoriesStatusNot(sortable, false, false)).thenReturn(prodList);
		List<product> prodList_test = prodService.retrieveProducts();
		assertEquals(list_size, prodList_test.size());
	}

	@Test
	public void retrieveProductsByTypeSuccess_Test() {
		Sort sortable = Sort.by("updateDate").descending();
		when(prodRepository.findByCategoriesIdAndStatusNot(sortable, 1, false)).thenReturn(prodList);
		List<product> prodList_test = prodService.retrieveProductsByType(1);
		assertEquals(list_size, prodList_test.size());
	}

	@Test
	public void createProdSuccess_Test() {
		when(cateService.getCategories(prodDTO.getCategoriesId())).thenReturn(Optional.of(cate1));
		when(personService.getPerson(person1.getId())).thenReturn(Optional.of(person1));
		when(prodRepository.save(Mockito.any(product.class))).thenReturn(prod1);
		product prod_test = prodService.createProduct(prodDTO, person1.getId());
		assertEquals(prod1, prod_test);
	}

	@Test
	public void createProdFailedNameExist_Test() {
		when(prodRepository.findByNameIgnoreCaseAndStatusNot(prod1.getName(), false)).thenReturn(prodList);
		assertThrows(ObjectAlreadyExistException.class, () -> prodService.createProduct(prodDTO, person1.getId()));
	}

	@Test
	public void createProdFailedIdExist_Test() {
		when(prodRepository.findByIdIgnoreCase(prod1.getId())).thenReturn(Optional.of(prod1));
		assertThrows(ObjectAlreadyExistException.class, () -> prodService.createProduct(prodDTO, person1.getId()));
	}

	@Test
	public void updateProdSuccess_Test() {
		List<product> emptyList = new ArrayList<product>();
		when(cateService.getCategories(prodDTO.getCategoriesId())).thenReturn(Optional.of(cate1));
		when(personService.getPerson(person1.getId())).thenReturn(Optional.of(person1));
		when(prodRepository.findByNameIgnoreCaseAndStatusNot(prodDTO.getName(), false)).thenReturn(emptyList);
		when(prodRepository.save(Mockito.any(product.class))).thenReturn(prod1);
		assertTrue(prodService.updateProduct(prodDTO, person1.getId()));
	}

	@Test
	public void updateProdFailedExistName_Test() {
		when(prodRepository.findByNameIgnoreCaseAndStatusNot(prod1.getName(), false)).thenReturn(prodList);
		assertThrows(ObjectAlreadyExistException.class, () -> prodService.updateProduct(prodDTO, person1.getId()));
	}

	@Test
	public void updateProdFailedCateNotFound_Test() {
		List<product> emptyList = new ArrayList<product>();
		when(prodRepository.findByNameIgnoreCaseAndStatusNot(prodDTO.getName(), false)).thenReturn(emptyList);
		assertThrows(ObjectNotFoundException.class, () -> prodService.updateProduct(prodDTO, person1.getId()));
	}

	@Test
	public void updateProductQuantitySuccess_Test() {
		prod3.setStatus(true);
		when(prodRepository.findByIdIgnoreCaseAndStatusNotAndCategoriesStatusNot(prodDTO.getId(), false, false))
				.thenReturn(Optional.of(prod3));
		when(prodRepository.save(Mockito.any(product.class))).thenReturn(prod1);
		assertTrue(prodService.updateProductQuantity(prodDTO.getId(), list_size));
	}
	
	@Test
	public void updateProductQuantityFailedProductDeleted_Test() {
		when(prodRepository.findByIdIgnoreCaseAndStatusNotAndCategoriesStatusNot(prodDTO.getId(), false, false))
				.thenReturn(Optional.of(prod3));
		when(prodRepository.save(Mockito.any(product.class))).thenReturn(prod1);
		assertThrows(ObjectNotFoundException.class, () -> prodService.updateProductQuantity(prodDTO.getId(), list_size));
	}
	
	@Test
	public void updateProductQuantityFailedNotEnoughQuantity_Test() {
		when(prodRepository.findByIdIgnoreCaseAndStatusNotAndCategoriesStatusNot(prodDTO.getId(), false, false))
				.thenReturn(Optional.of(prod1));
		when(prodRepository.save(Mockito.any(product.class))).thenReturn(prod1);
		assertThrows(ObjectPropertiesIllegalException.class, () -> prodService.updateProductQuantity(prodDTO.getId(), list_size));
	}

	@Test
	public void deleteProductSuccess_Test() {
		when(personService.getPerson(person1.getId())).thenReturn(Optional.of(person1));
		when(prodRepository.findByIdIgnoreCaseAndStatusNotAndCategoriesStatusNot(prod1.getId(), false, false))
				.thenReturn(Optional.of(prod1));
		when(prodRepository.save(Mockito.any(product.class))).thenReturn(prod1);
		assertTrue(prodService.deleteProduct(prod1.getId(), person1.getId()));
	}

}
