package com.nashtech.FutsalShop.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.FutsalShop.DTO.OrderDTO;
import com.nashtech.FutsalShop.Utils.StringUtils;
import com.nashtech.FutsalShop.model.Order;
import com.nashtech.FutsalShop.exception.ObjectNotFoundException;
import com.nashtech.FutsalShop.exception.ObjectPropertiesIllegalException;
import com.nashtech.FutsalShop.exception.WrongInputTypeException;
import com.nashtech.FutsalShop.security.JWT.JwtAuthTokenFilter;
import com.nashtech.FutsalShop.security.JWT.JwtUtils;
import com.nashtech.FutsalShop.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private JwtUtils jwtUtils;

	private static final Logger logger = Logger.getLogger(OrderController.class);

	@Operation(summary = "Get total of Order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/order/totalOrder")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public long getNumberOfOrders() {
		return orderService.countTotal();
	}

	@GetMapping("/order/totalOrderByUser/{email}")
	@PreAuthorize("hasRole('USER')")
	public long getNumberOfOrdersByUser(@PathVariable(name = "email") String email) {
		return orderService.countTotalOrderByUser(email);
	}

	@GetMapping("/order/totalOrderByStatus/{status}")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public long getNumberOfOrdersByStatus(@PathVariable(name = "status") int status) {
		return orderService.countByStatus(status);
	}

	@GetMapping("/order/search/OrderByCustomer")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public List<OrderDTO> searchOrderByCustomer(@RequestParam(name = "keyword") String keyword,
			@RequestParam(name = "status", required = false) Integer status) {
		if (status == null)
			return orderService.searchOrderByCustomer(keyword).stream().map(orderService::convertToDTO)
					.collect(Collectors.toList());
		return orderService.searchOrderByStatusAndCustomer(keyword, status).stream().map(orderService::convertToDTO)
				.collect(Collectors.toList());
	}

	@Operation(summary = "Find Order by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/order/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
	public Order findOrder(@PathVariable(name = "id") int id) {
		return orderService.getOrder(id)
				.orElseThrow(() -> new ObjectNotFoundException("Could not find order with Id: " + id));
	}
	
	@GetMapping("/orderDTO/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
	public OrderDTO findOrderDTO(@PathVariable(name = "id") int id) {
		Order order = orderService.getOrder(id)
				.orElseThrow(() -> new ObjectNotFoundException("Could not find order with Id: " + id));
		 return orderService.convertToDTO(order);
	}

	@Operation(summary = "Create Order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/order")
	@PreAuthorize("hasRole('USER')")
	public Order createOrder(@RequestBody OrderDTO newOrder) {
		return orderService.createOrder(newOrder);
	}

	@Operation(summary = "Delete Order by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@DeleteMapping("/order/{id}")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public String deleteOrder(@PathVariable(name = "id") int id) {
		try {
			return orderService.deleteOrder(id) ? StringUtils.TRUE : StringUtils.FALSE;
		} catch (NoSuchElementException ex) {
			throw new ObjectNotFoundException(ex.getMessage());
		}
	}

	@Operation(summary = "Update Order")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PutMapping("/order/{id}")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public String updateOrder(@RequestBody OrderDTO order, @PathVariable(name = "id") int id) {
		try {
			return orderService.updateOrder(order) ? StringUtils.TRUE : StringUtils.FALSE;
		} catch (NoSuchElementException ex) {
			throw new ObjectNotFoundException("Could not find Order with id: " + order.getId());
		}

	}

	@PutMapping("/order/payment/{id}")
	@PreAuthorize("hasRole('USER')")
	public String updateOrderPayment(HttpServletRequest request, @PathVariable(name = "id") int id) {
		String jwt = JwtAuthTokenFilter.parseJwt(request);
		String userId = jwtUtils.getUserNameFromJwtToken(jwt);
		try {
			return orderService.updateOrderPayment(id, Integer.parseInt(userId)) ? StringUtils.TRUE : StringUtils.FALSE;
		} catch (NoSuchElementException ex) {
			logger.error("Account id " + userId + " update order status with Id " + id
					+ " failed: Could not find Order with id: " + id);
			throw new ObjectNotFoundException("Could not find Order with id: " + id);
		}

	}

	@Operation(summary = "Get Order by Customer")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/order/customeremail")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
	public List<Order> findListOrderedByCustomerEmail(@RequestParam(name = "pagenum") int page,
                                                      @RequestParam(name = "size") int size, @RequestParam(name = "email") String email) {
		return orderService.getOrderByCustomerEmail(page, size, email);
	}

	@Operation(summary = "Get Order by CustomerID ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/order/customerid")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
	public List<Order> findListOrderedByCustomerId(@RequestParam(name = "pagenum") int page,
                                                   @RequestParam(name = "size") int size, @RequestParam(name = "id") int id) {
		return orderService.getOrdersByCustomerPages(page, size, id);
	}

	@Operation(summary = "Get Order by Page")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/order")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
	public List<Order> getOrder(@RequestParam(name = "pagenum") int page, @RequestParam(name = "size") int size) {
		return orderService.getOrderPage(page, size);
	}

	@GetMapping("/orderDTO")
	public List<OrderDTO> getOrderDTOPage(@RequestParam(name = "pagenum") int page,
			@RequestParam(name = "size") int size, @RequestParam(name = "status", required = false) Integer status) {
		if (status == null)
			return orderService.getOrderPage(page, size).stream().map(orderService::convertToDTO)
					.collect(Collectors.toList());
		else
			return orderService.getOrderPageByStatus(page, size, status).stream().map(orderService::convertToDTO)
					.collect(Collectors.toList());
	}

	@PutMapping("/order/updateStatus/{id}")
	@PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
	public String updateStatusOrder(HttpServletRequest request, @PathVariable(name = "id") int id,
			@RequestParam(name = "status") int status) {
		String jwt = JwtAuthTokenFilter.parseJwt(request);
		String userId = jwtUtils.getUserNameFromJwtToken(jwt);
		if (status < 0 || status > 4) {
			logger.error(
					"Account id " + userId + " update order status with Id " + id + " failed: Wrong status " + status);
			throw new WrongInputTypeException("Wrong status");
		}
		return orderService.updateStatusOrder(id, status, userId) ? StringUtils.TRUE : StringUtils.FALSE;
	}
	
	@PutMapping("/order/note/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
	public String updateStatusOrder(HttpServletRequest request, @PathVariable(name = "id") int id,
			@RequestParam(name = "status") int status, @RequestParam(name = "note") String note) {
		String jwt = JwtAuthTokenFilter.parseJwt(request);
		String userId = jwtUtils.getUserNameFromJwtToken(jwt);
		if (status < 0 || status > 4) {
			logger.error(
					"Account id " + userId + " update order status with Id " + id + " failed: Wrong status " + status);
			throw new WrongInputTypeException("Wrong status");
		}
		return orderService.updateNoteOrder(id, status, userId, note) ? StringUtils.TRUE : StringUtils.FALSE;
	}

	@GetMapping("/order/report/profitByMonth")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
	public float getProfitByMonth(HttpServletRequest request, @RequestParam(name = "month") int month,
			@RequestParam(name = "year") int year) {
		String jwt = JwtAuthTokenFilter.parseJwt(request);
		String userId = jwtUtils.getUserNameFromJwtToken(jwt);
		if (month > 12 || month < 1) {
			logger.error("Account id " + userId + " get profit failed: Month must from 1 to 12");
			throw new ObjectPropertiesIllegalException("Month must from 1 to 12");
		}
		if (year == 0 || year < 2000) {
			logger.error("Account id " + userId + " get profit failed: Year is invalid");
			throw new ObjectPropertiesIllegalException("Year is invalid");
		}
		return orderService.profitByMonth(month, year);
	}
	
}
