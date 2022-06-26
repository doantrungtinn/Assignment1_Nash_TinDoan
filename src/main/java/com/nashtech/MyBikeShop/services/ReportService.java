package com.nashtech.MyBikeShop.services;

import java.util.List;

import com.nashtech.MyBikeShop.DTO.ReportProdProcess;
import com.nashtech.MyBikeShop.DTO.ReportTopProduct;
import com.nashtech.MyBikeShop.model.product;

public interface ReportService {
	public List<Float> profitByYear(int year);
	
	public List<Float> purchaseCostByYear(int year);
	
	public List<ReportTopProduct> topProductByMonth(String fromMonth, String toMonth);
	
	public List<ReportProdProcess> productProccess(int cateId);
	
	public List<product> hotProduct(int size);
	
	public List<Float> profitMonth(String fromMonth, String toMonth);
	
	public double profitAvgMonth(int month, int year);
}
