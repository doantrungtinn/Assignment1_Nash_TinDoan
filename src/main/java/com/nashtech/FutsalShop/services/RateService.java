package com.nashtech.FutsalShop.services;

import java.util.List;

import com.nashtech.FutsalShop.DTO.RateDTO;
import com.nashtech.FutsalShop.model.rate;

public interface RateService {
	public List<rate> getRateByProduct(String id);

	public List<rate> getRateProductPage(String id, int pageNum, int size);

	public rate createRate(RateDTO rate);

	public boolean deleteRate(int id, String email);

	public boolean updateRate(RateDTO rate);

	public int getNumRate(String id);

	public boolean checkExist(String prodId, int customerId);

	public double getAverageRateNumByProduct(String id);
}
