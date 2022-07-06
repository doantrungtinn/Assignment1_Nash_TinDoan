package com.nashtech.FutsalShop.services.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.FutsalShop.model.orderdetail;
import com.nashtech.FutsalShop.model.order;
import com.nashtech.FutsalShop.model.product;
import com.nashtech.FutsalShop.repository.OrderDetailRepository;
import com.nashtech.FutsalShop.services.OrderDetailService;
import com.nashtech.FutsalShop.services.OrderService;
import com.nashtech.FutsalShop.services.ProductService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	OrderDetailRepository orderDetailRepo;

	@Autowired
	OrderService orderService;

	@Autowired
	ProductService productService;

	public OrderDetailServiceImpl() {
		super();
	}

	public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepo) {
		this.orderDetailRepo = orderDetailRepo;
	}

	public List<orderdetail> retrieveOrders() {
		return orderDetailRepo.findAll();
	}

	public Set<orderdetail> getDetailOrderByOrderId(int id) {
		return orderDetailRepo.findByIdOrderId(id);
	}

	@Transactional
	public boolean createDetail(orderdetail order) {
		boolean result = productService.updateProductQuantity(order.getId().getProductId(), order.getAmmount() * (-1));
		if (!result)
			return false;
		orderDetailRepo.save(order);
		return true;
	}

	@Transactional
	public boolean deleteDetail(orderdetail orderDetailEntity) {
		int orderId = orderDetailEntity.getId().getOrderId();
		order order = orderService.getOrder(orderId).get();
		if (order.getStatus() != 4) { // False = Not delivery yet
			boolean result = productService.updateProductQuantity(orderDetailEntity.getId().getProductId(),
					orderDetailEntity.getAmmount());
			if (!result)
				return false;

		}
//		orderDetailRepo.delete(orderDetailEntity);
		return true;
	}

	@Transactional
	public boolean updateDetailCancel(orderdetail orderDetailEntity) {
//		ProductEntity prod = productService.getProduct(orderDetailEntity.getId().getProductId()).get();
		boolean result = productService.updateProductQuantityToCancel(orderDetailEntity.getId().getProductId(),
				orderDetailEntity.getAmmount());
		if (!result)
			return false;
		return true;
	}

	@Transactional
	public boolean updateDetail(orderdetail orderDetailEntity) {
		product prod = productService.getProduct(orderDetailEntity.getId().getProductId()).get();
		boolean result = productService.updateProductQuantity(orderDetailEntity.getId().getProductId(),
				orderDetailEntity.getAmmount() * (-1));
		if (!result)
			return false;
		return true;
	}
}
