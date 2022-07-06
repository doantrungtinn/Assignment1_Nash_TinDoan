package com.nashtech.FutsalShop.services.impl.reportMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nashtech.FutsalShop.model.product;

public class ProductMapper implements RowMapper<product>{
	
	@Override
	public product mapRow(ResultSet rs, int rowNum) throws SQLException {
		product prod = new product();
		prod.setId(rs.getString("id"));
		prod.setName(rs.getString("name"));
		prod.setPrice(rs.getFloat("price"));
		prod.setQuantity(rs.getInt("quantity"));
		prod.setPhoto(rs.getBytes("photo"));
		return prod;
	}
}
