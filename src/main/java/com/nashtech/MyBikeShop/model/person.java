package com.nashtech.MyBikeShop.model;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nashtech.MyBikeShop.DTO.PersonDTO;

@Entity
@Table(name = "persons")
public class person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "fullname")
	private String fullname;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dob")
	private Date dob;

	@Column(name = "gender")
	private boolean gender;

	@Column(name = "address")
	private String address;

	@Column(name = "phonenumber")
	private String phonenumber;

	@Column(name = "role")
	private String role;

	@Column(name = "status")
	private boolean status;

	@OneToMany(mappedBy = "customers", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<order> orders;
	
	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<order> orders_Employee;

	@OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<orderimport> ordersImport;

	@OneToMany(mappedBy = "employeeUpdate", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<com.nashtech.MyBikeShop.model.product> product;

	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	Set<rate> reviews;

	public person() {
		super();
	}

	public person(int id, String email, String password, String fullname, String role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.role = role;
	}

	public person(PersonDTO personDTO) {
		super();
		this.id = personDTO.getId();
		this.email = personDTO.getEmail();
		this.password = personDTO.getPassword();
		this.fullname = personDTO.getFullname();
		this.dob = personDTO.getDob();
		this.gender = personDTO.isGender();
		this.address = personDTO.getAddress();
		this.phonenumber = personDTO.getPhonenumber();
		this.status = personDTO.isStatus();
		this.role = personDTO.getRole();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Set<rate> getReviews() {
		return reviews;
	}

	public void setReviews(Set<rate> reviews) {
		this.reviews = reviews;
	}

	public Set<order> getOrders() {
		return orders;
	}

	public void setOrders(Set<order> orders) {
		this.orders = orders;
	}

	public Set<order> getOrders_Employee() {
		return orders_Employee;
	}

	public void setOrders_Employee(Set<order> orders_Employee) {
		this.orders_Employee = orders_Employee;
	}

	public Set<orderimport> getOrdersImport() {
		return ordersImport;
	}

	public void setOrdersImport(Set<orderimport> ordersImport) {
		this.ordersImport = ordersImport;
	}

	public Set<com.nashtech.MyBikeShop.model.product> getProduct() {
		return product;
	}

	public void setProduct(Set<com.nashtech.MyBikeShop.model.product> product) {
		this.product = product;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		person other = (person) obj;
		return Objects.equals(address, other.address) && Objects.equals(dob, other.dob)
				&& Objects.equals(email, other.email) && Objects.equals(fullname, other.fullname)
				&& gender == other.gender && id == other.id && Objects.equals(orders, other.orders)
				&& Objects.equals(ordersImport, other.ordersImport) && Objects.equals(password, other.password)
				&& Objects.equals(phonenumber, other.phonenumber) && Objects.equals(product, other.product)
				&& Objects.equals(reviews, other.reviews) && Objects.equals(role, other.role) && status == other.status;
	}

}
