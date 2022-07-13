package com.nashtech.FutsalShop.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nashtech.FutsalShop.DTO.ProductDTO;
import com.nashtech.FutsalShop.exception.ObjectPropertiesIllegalException;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@NotNull
	@Column(name = "id")
	private String id;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@DecimalMin(value = "0", message = "Price must be not under 0")
	@Column(name = "price")
	private float price;

	@NotNull
	@DecimalMin(value = "0", message = "Quantity must be not under 0")
	@Column(name = "quantity")
	private int quantity;

	@Column(name = "description")
	private String description;

	@Column(name = "brand")
	private String brand;

	@Column(name = "createdate")
	private LocalDateTime createDate;

	@Column(name = "updatedate")
	private LocalDateTime updateDate;
	
	@Column(name = "status")
	private boolean status;


	@Column(name = "photo")
	private String photo;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	Set<Orderdetail> orderDetails;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	Set<Orderimportdetail> orderImportDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	Set<Rate> reviews;

	@ManyToOne
	@JoinColumn(name = "producttype")
	private Categories categories;
	
	@ManyToOne
	@JoinColumn(name = "updateby")
	private Person employeeUpdate;

	public Product() {
		super();
	}

	public Product(@NotNull String id, @NotNull String name, @NotNull float price, @NotNull int quantity,
				   Categories categories) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.categories = categories;
	}

	public Product(ProductDTO product, Categories cate) {
		super();
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.quantity = product.getQuantity();
		this.categories = cate;
		this.createDate = product.getCreateDate();
		this.description = product.getDescription();
		this.photo = product.getPhoto();
		this.brand = product.getBrand();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		StringBuilder idTrim = new StringBuilder();
		idTrim.append(id.trim());
		if (idTrim.length() == 0) {
			throw new IllegalArgumentException("ID is invalid");
		}
		this.id = idTrim.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		StringBuilder nameTrim = new StringBuilder();
		nameTrim.append(name.trim());
		if (nameTrim.length() == 0) {
			throw new IllegalArgumentException("Name is invalid");
		}
		this.name = nameTrim.toString();
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		if (price < 0) {
			throw new IllegalArgumentException("Price is invalid");
		}
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void changeQuantity(int numChange) {
		boolean checkNumberChange = ((numChange < 0) && (this.quantity < Math.abs(numChange)));
		if (checkNumberChange)
			throw new ObjectPropertiesIllegalException("Quantity of product is not enough to update");
		this.quantity += numChange;
	}

	public void setQuantity(int quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity is invalid");
		}
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Categories getCategories() {
		return categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	public Set<Rate> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Rate> reviews) {
		this.reviews = reviews;
	}
	
	public Set<Orderdetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<Orderdetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Set<Orderimportdetail> getOrderImportDetails() {
		return orderImportDetails;
	}

	public void setOrderImportDetails(Set<Orderimportdetail> orderImportDetails) {
		this.orderImportDetails = orderImportDetails;
	}

	public Person getEmployeeUpdate() {
		return employeeUpdate;
	}

	public void setEmployeeUpdate(Person employeeUpdate) {
		this.employeeUpdate = employeeUpdate;
	}

	@Override
	public String toString() {
		return "ID: " + this.id + "\t Name: " + this.name + "\t Price:" + this.price + "\t Quantity: " + this.quantity
				+ "\t Type: " + this.categories.getName() + "\t Brand: " + this.brand;
	}

	@Override
	public int hashCode() {
		return Objects.hash(brand, categories, createDate, description, id, name, orderDetails, price, quantity,
				reviews, updateDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(orderDetails, other.orderDetails)
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price) && quantity == other.quantity
				&& Objects.equals(reviews, other.reviews) && Objects.equals(updateDate, other.updateDate);
	}

}
