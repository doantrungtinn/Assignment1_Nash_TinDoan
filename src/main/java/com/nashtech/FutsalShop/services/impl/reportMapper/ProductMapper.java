package com.nashtech.FutsalShop.services.impl.reportMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nashtech.FutsalShop.model.Product;

public class ProductMapper implements RowMapper<Product>{
	
	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product prod = new Product();
		prod.setId(rs.getString("id"));
		prod.setName(rs.getString("name"));
		prod.setPrice(rs.getFloat("price"));
		prod.setQuantity(rs.getInt("quantity"));
		prod.setPhoto(rs.getString("photo"));
		return prod;
	}
}
