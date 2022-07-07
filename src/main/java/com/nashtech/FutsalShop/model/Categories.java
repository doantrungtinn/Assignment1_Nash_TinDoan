package com.nashtech.FutsalShop.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nashtech.FutsalShop.DTO.CategoriesDTO;

@Entity
@Table(name = "categories")
public class Categories {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private boolean status;

	@OneToMany(mappedBy = "categories", fetch = FetchType.EAGER)
	@JsonIgnore
	private Collection<Product> product;

	public Categories() {
		super();
	}

	public Categories(int id) {
		super();
		this.id = id;
	}

	public Categories(CategoriesDTO categories) {
		this.id = categories.getId();
		this.name = categories.getName();
		this.description = categories.getDescription();
	}
	
	

	public Categories(int id, String categoriesname, String description, boolean status) {
		super();
		this.id = id;
		this.name = categoriesname;
		this.description = description;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Collection<Product> getProduct() {
		return product;
	}

	public void setProduct(Collection<Product> product) {
		this.product = product;
	}

}
