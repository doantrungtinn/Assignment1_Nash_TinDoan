package com.nashtech.MyBikeShop.services;

import java.util.List;

import com.nashtech.MyBikeShop.DTO.OrderImportDTO;
import org.springframework.web.multipart.MultipartFile;
import com.nashtech.MyBikeShop.model.orderimport;

public interface OrderImportService {
	public orderimport createOrderImport (orderimport orderImport, int userId);
	
	public orderimport convertToEntity (OrderImportDTO orderImportDto);
	
	public OrderImportDTO convertToDto (orderimport orderImport);
	
	public List<orderimport> getOrderImportPage(int num, int size);
	
	public List<orderimport> searchOrderImportByEmployee(String keyword);
	
	public orderimport findOrderImportById (int importId);
	
	public orderimport updateOrderImport(OrderImportDTO orderImportDto, int orderImportId, int userId);
	
	public boolean deleteOrderImport (int orderImportId);
	
	public float purchaseCostByMonth (int month, int year);
	
	public orderimport createOrderFromXLSS(MultipartFile reapExcelDataFile, String email);
	
	public long countTotal();
	
	public int getLatestId();
	
	public int generateNewId();
	
	public List<orderimport> getImportByProductId(String prodId);
	
}
