package com.nashtech.FutsalShop.services;

import java.util.List;
import java.util.Set;

import com.nashtech.FutsalShop.model.Orderdetail;

public interface OrderDetailService {
	public List<Orderdetail> retrieveOrders();

	public Set<Orderdetail> getDetailOrderByOrderId(int id);

	public boolean createDetail(Orderdetail orDetail);

	public boolean deleteDetail(Orderdetail orderDetailEntity);
	
	public boolean updateDetail(Orderdetail orderDetailEntity);
	
	public boolean updateDetailCancel(Orderdetail orderDetailEntity);
}
