package com.nashtech.FutsalShop.Service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.nashtech.FutsalShop.services.OrderImportDetailService;
import com.nashtech.FutsalShop.services.OrderImportService;
import com.nashtech.FutsalShop.services.PersonService;
import com.nashtech.FutsalShop.services.ProductService;
import com.nashtech.FutsalShop.services.impl.OrderImportDetailServiceImpl;

@SpringBootTest
public class OrderImportDetailServiceImplTest {
	@InjectMocks
	OrderImportDetailService detailImportService = new OrderImportDetailServiceImpl();
	
	@Mock
	ProductService productService;

	@Mock
	ModelMapper mapper;

	@Mock
	PersonService personService;
	
	@Mock
	OrderImportService importService;
}
