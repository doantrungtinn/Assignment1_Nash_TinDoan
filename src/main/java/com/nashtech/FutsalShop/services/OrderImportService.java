package com.nashtech.FutsalShop.services;

import java.util.List;

import com.nashtech.FutsalShop.DTO.OrderImportDTO;
import org.springframework.web.multipart.MultipartFile;
import com.nashtech.FutsalShop.model.Orderimport;

public interface OrderImportService {
	public Orderimport createOrderImport (Orderimport orderImport, int userId);
	
	public Orderimport convertToEntity (OrderImportDTO orderImportDto);
	
	public OrderImportDTO convertToDto (Orderimport orderImport);
	
	public List<Orderimport> getOrderImportPage(int num, int size);
	
	public List<Orderimport> searchOrderImportByEmployee(String keyword);
	
	public Orderimport findOrderImportById (int importId);
	
	public Orderimport updateOrderImport(OrderImportDTO orderImportDto, int orderImportId, int userId);
	
	public boolean deleteOrderImport (int orderImportId);
	
	public float purchaseCostByMonth (int month, int year);
	
	public Orderimport createOrderFromXLSS(MultipartFile reapExcelDataFile, String email);
	
	public long countTotal();
	
	public int getLatestId();
	
	public int generateNewId();
	
	public List<Orderimport> getImportByProductId(String prodId);
	
}
