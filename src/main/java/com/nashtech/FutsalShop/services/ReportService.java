package com.nashtech.FutsalShop.services;

import java.util.List;

import com.nashtech.FutsalShop.DTO.ReportProdProcess;
import com.nashtech.FutsalShop.DTO.ReportTopProduct;
import com.nashtech.FutsalShop.model.product;

public interface ReportService {
	public List<Float> profitByYear(int year);
	
	public List<Float> purchaseCostByYear(int year);
	
	public List<ReportTopProduct> topProductByMonth(String fromMonth, String toMonth);
	
	public List<ReportProdProcess> productProccess(int cateId);
	
	public List<product> hotProduct(int size);
	
	public List<Float> profitMonth(String fromMonth, String toMonth);
	
	public double profitAvgMonth(int month, int year);
}
