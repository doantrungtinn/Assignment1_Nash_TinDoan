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

import com.nashtech.FutsalShop.DTO.OrderImportDTO;

@Entity
@Table(name = "orderimport")
public class Orderimport {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "timeimport")
	private LocalDateTime timeimport;

//	@Column(name = "totalcost")
//	private Double totalCost;

	@Column(name = "status")
	private boolean status;

	@ManyToOne
	@JoinColumn(name = "employeeid")
	private Person employee;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	Set<Orderimportdetail> orderImportDetails;

	public Orderimport() {
		super();
	}

	public Orderimport(int id, boolean status, Person employee,
					   Set<Orderimportdetail> orderImportDetails) {
		super();
		this.id = id;
		this.status = status;
		this.employee = employee;
		this.orderImportDetails = orderImportDetails;
	}

	public Orderimport(int id, LocalDateTime timeimport, boolean status, Person employee,
					   Set<Orderimportdetail> orderDetails) {
		super();
		this.id = id;
		this.timeimport = timeimport;
//		this.totalCost = totalCost;
		this.status = status;
		this.employee = employee;
		this.orderImportDetails = orderDetails;
	}

	public Orderimport(OrderImportDTO order) {
		super();
		this.id = order.getId();
//		this.totalCost = order.getTotalCost();
		this.status = order.isStatus();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getTimeimport() {
		return timeimport;
	}

	public void setTimeimport(LocalDateTime timeimport) {
		this.timeimport = timeimport;
	}

	public Person getEmployee() {
		return employee;
	}

	public void setEmployee(Person employee) {
		this.employee = employee;
	}

//	public Double getTotalCost() {
//		return totalCost;
//	}
//
//	public void setTotalCost(Double totalCost) {
//		if (totalCost < 0) {
//			throw new IllegalArgumentException("Total must not below zero");
//		}
//		this.totalCost = totalCost;
//	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Set<Orderimportdetail> getOrderImportDetails() {
		return orderImportDetails;
	}

	public void setOrderImportDetails(Set<Orderimportdetail> orderImportDetails) {
		this.orderImportDetails = orderImportDetails;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orderimport other = (Orderimport) obj;
		return Objects.equals(employee, other.employee) && id == other.id
				&& Objects.equals(orderImportDetails, other.orderImportDetails) && status == other.status
				&& Objects.equals(timeimport, other.timeimport);
	}

	@Override
	public String toString() {
		return "OrderImportEntity [id=" + id + ", timeimport=" + timeimport + ", status=" + status + ", employee="
				+ employee;
	}

}
