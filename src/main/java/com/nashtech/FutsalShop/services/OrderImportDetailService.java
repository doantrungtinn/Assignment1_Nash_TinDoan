package com.nashtech.FutsalShop.services;

import com.nashtech.FutsalShop.DTO.OrderImportDetailDTO;
import com.nashtech.FutsalShop.model.Orderimportdetail;

public interface OrderImportDetailService {
	
	public Orderimportdetail convertToEntity (OrderImportDetailDTO orderImportDetailDto);
	
	public OrderImportDetailDTO convertToDto (Orderimportdetail orderImportDetail);
}
