package com.nashtech.FutsalShop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.FutsalShop.DTO.OrderImportDetailDTO;
import com.nashtech.FutsalShop.model.orderimportdetail;
import com.nashtech.FutsalShop.model.orderimportdetail.OrderImportDetailsKey;
import com.nashtech.FutsalShop.model.orderimport;
import com.nashtech.FutsalShop.model.product;
import com.nashtech.FutsalShop.services.OrderImportDetailService;
import com.nashtech.FutsalShop.services.OrderImportService;
import com.nashtech.FutsalShop.services.PersonService;
import com.nashtech.FutsalShop.services.ProductService;

@Service
public class OrderImportDetailServiceImpl implements OrderImportDetailService {
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	OrderImportService importService;

	public OrderImportDetailServiceImpl() {
		super();
	}

	@Override
	public orderimportdetail convertToEntity(OrderImportDetailDTO orderImportDetailDto) {
		orderimportdetail importDetail = mapper.map(orderImportDetailDto, orderimportdetail.class);
		OrderImportDetailsKey importKey = new OrderImportDetailsKey(orderImportDetailDto.getOrderImportId(),
				orderImportDetailDto.getProductId());
		importDetail.setId(importKey);
		product product = productService.getProduct(orderImportDetailDto.getProductId()).orElse(null);
		orderimport orderImport = importService.findOrderImportById(orderImportDetailDto.getOrderImportId());
		importDetail.setProduct(product);
		importDetail.setOrder(orderImport);
		importDetail.setAmmount(orderImportDetailDto.getAmount());
		importDetail.setPrice(orderImportDetailDto.getUnitprice());
		return importDetail;
	}

	@Override
	public OrderImportDetailDTO convertToDto(orderimportdetail orderImportDetail) {
		OrderImportDetailDTO importDetailDto = mapper.map(orderImportDetail, OrderImportDetailDTO.class);
		importDetailDto.setOrderImportId(orderImportDetail.getId().getOrderId());
		importDetailDto.setProductId(orderImportDetail.getId().getProductId());
		importDetailDto.setAmount(orderImportDetail.getAmmount());
		importDetailDto.setUnitprice(orderImportDetail.getPrice());
		return importDetailDto;
	}

}
