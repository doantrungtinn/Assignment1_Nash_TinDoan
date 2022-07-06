package com.nashtech.FutsalShop.services;

import com.nashtech.FutsalShop.DTO.OrderImportDetailDTO;
import com.nashtech.FutsalShop.model.orderimportdetail;

public interface OrderImportDetailService {
	
	public orderimportdetail convertToEntity (OrderImportDetailDTO orderImportDetailDto);
	
	public OrderImportDetailDTO convertToDto (orderimportdetail orderImportDetail);
}
