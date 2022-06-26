package com.nashtech.MyBikeShop.services;

import java.util.List;
import java.util.Set;

import com.nashtech.MyBikeShop.model.orderdetail;

public interface OrderDetailService {
	public List<orderdetail> retrieveOrders();

	public Set<orderdetail> getDetailOrderByOrderId(int id);

	public boolean createDetail(orderdetail orDetail);

	public boolean deleteDetail(orderdetail orderDetailEntity);
	
	public boolean updateDetail(orderdetail orderDetailEntity);
	
	public boolean updateDetailCancel(orderdetail orderDetailEntity);
}
