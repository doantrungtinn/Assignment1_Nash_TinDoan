package com.nashtech.FutsalShop.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.nashtech.FutsalShop.DTO.OrderDTO;

@Entity
@Table(name = "orderbill")
public class Order {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "timebought")
	private LocalDateTime timebought;

	@Column(name = "address")
	private String address;

	@Column(name = "status")
	private int status;
	
	@Column(name = "ispay")
	private boolean payment;
	
	@Column(name = "note")
	private String note;

	@ManyToOne
	@JoinColumn(name = "customerid")
	private Person customers;
	
	@ManyToOne
	@JoinColumn(name = "approveby")
	private Person employee;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	Set<Orderdetail> orderDetails;

	public Order() {
		super();
	}

	public Order(int id, String address, int status, Person customers) {
		super();
		this.id = id;
		this.address = address;
		this.status = status;
		this.customers = customers;
	}

	public Order(OrderDTO order) {
		super();
		this.id = order.getId();
		this.payment = order.isPayment();
		this.address = order.getAddress();
		this.status = order.isStatus();
		this.note = order.getNote();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getCustomers() {
		return customers;
	}

	public void setCustomers(Person customers) {
		this.customers = customers;
	}

	public LocalDateTime getTimebought() {
		return timebought;
	}

	public void setTimebought(LocalDateTime timebought) {
		this.timebought = timebought;
	}

	public boolean isPayment() {
		return payment;
	}

	public void setPayment(boolean payment) {
		this.payment = payment;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set<Orderdetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<Orderdetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Person getEmployee() {
		return employee;
	}

	public void setEmployee(Person employee) {
		this.employee = employee;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(address, other.address) && Objects.equals(customers, other.customers) && id == other.id
				&& Objects.equals(orderDetails, other.orderDetails) && status == other.status
				&& Objects.equals(timebought, other.timebought);
	}

}
