package com.nashtech.FutsalShop.services;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.nashtech.FutsalShop.DTO.OrderDTO;
import com.nashtech.FutsalShop.model.Order;

public interface OrderService {
	public List<Order> retrieveOrders();

	public Optional<Order> getOrder(int id);

	public List<Order> getOrdersByCustomerPages(int num, int size, int id);

	public List<Order> getOrderPage(int num, int size);
	
	public List<Order> getOrderPageByStatus(int num, int size, int status);
	
	public List<Order> searchOrderByCustomer(String keyword);
	
	public List<Order> searchOrderByStatusAndCustomer(String keyword, int status);
	
	public boolean checkOrderedByProductAndCustomerId(String prodId, int customerId);

	public Order createOrder(OrderDTO order);

	public boolean deleteOrder(int id);

	public boolean updateOrder(OrderDTO order);
	
	public boolean updateOrderPayment(int id, int customerEmail);

	public boolean updateStatusOrder(int id, int status, String userId);
	
	public boolean updateNoteOrder(int id, int status, String userId, String note);

	public List<Order> getOrderByCustomerEmail(int num, int size, String email);

	public void sendSimpleMessage(String to, String listProd, Double totalCost) throws MessagingException;

	public long countTotal();
	
	public long countByStatus(int status);
	
	public long countTotalOrderByUser(String email);
	
	public float profitByMonth(int month, int year);
	
	public OrderDTO convertToDTO(Order order);
	
	public int getLatestId();
	
	public int generateNewId();
	
	public double calculateProfitMonth(int month, int year);
}
