package com.nashtech.MyBikeShop.services;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.nashtech.MyBikeShop.DTO.OrderDTO;
import com.nashtech.MyBikeShop.model.order;

public interface OrderService {
	public List<order> retrieveOrders();

	public Optional<order> getOrder(int id);

	public List<order> getOrdersByCustomerPages(int num, int size, int id);

	public List<order> getOrderPage(int num, int size);
	
	public List<order> getOrderPageByStatus(int num, int size, int status);
	
	public List<order> searchOrderByCustomer(String keyword);
	
	public List<order> searchOrderByStatusAndCustomer(String keyword, int status);
	
	public boolean checkOrderedByProductAndCustomerId(String prodId, int customerId);

	public order createOrder(OrderDTO order);

	public boolean deleteOrder(int id);

	public boolean updateOrder(OrderDTO order);
	
	public boolean updateOrderPayment(int id, int customerEmail);

	public boolean updateStatusOrder(int id, int status, String userId);
	
	public boolean updateNoteOrder(int id, int status, String userId, String note);

	public List<order> getOrderByCustomerEmail(int num, int size, String email);

	public void sendSimpleMessage(String to, String listProd, Double totalCost) throws MessagingException;

	public long countTotal();
	
	public long countByStatus(int status);
	
	public long countTotalOrderByUser(String email);
	
	public float profitByMonth(int month, int year);
	
	public OrderDTO convertToDTO(order order);
	
	public int getLatestId();
	
	public int generateNewId();
	
	public double calculateProfitMonth(int month, int year);
}
