package com.nashtech.FutsalShop.services.impl.reportMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nashtech.FutsalShop.DTO.ReportProdProcess;

public class ReportProductProcessMapper implements RowMapper<ReportProdProcess>{
	
	@Override
	public ReportProdProcess  mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReportProdProcess report = new ReportProdProcess();
		report.setId(rs.getString("id"));
		report.setName(rs.getString("name"));
		report.setQuantity(rs.getInt("quantity"));
		report.setInProcess(rs.getInt("inprocess"));
		report.setDelivery(rs.getInt("delivery"));
		report.setCompleted(rs.getInt("completed"));
		report.setCancel(rs.getInt("cancel"));
		return report;
	}
}
