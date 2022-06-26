package com.nashtech.MyBikeShop.services;

import com.nashtech.MyBikeShop.DTO.OrderImportDetailDTO;
import com.nashtech.MyBikeShop.model.orderimportdetail;

public interface OrderImportDetailService {
	
	public orderimportdetail convertToEntity (OrderImportDetailDTO orderImportDetailDto);
	
	public OrderImportDetailDTO convertToDto (orderimportdetail orderImportDetail);
}
