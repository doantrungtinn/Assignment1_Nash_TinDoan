package com.nashtech.FutsalShop.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.FutsalShop.DTO.RateDTO;
import com.nashtech.FutsalShop.Utils.StringUtils;
import com.nashtech.FutsalShop.model.Product;
import com.nashtech.FutsalShop.model.Rate;
import com.nashtech.FutsalShop.model.Rate.RateKey;
import com.nashtech.FutsalShop.security.JWT.JwtAuthTokenFilter;
import com.nashtech.FutsalShop.security.JWT.JwtUtils;
import com.nashtech.FutsalShop.services.RateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class RateController {
	@Autowired
	RateService rateService;
	
	@Autowired
	private JwtUtils jwtUtils;

	@Operation(summary = "Create a Rate for Product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden: Only User can create a review", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PostMapping("/product/rate")
	@PreAuthorize("hasRole('roleUSER') or hasRole('STAFF') or hasRole('ADMIN')")
	public Rate createRateOfProduct(@RequestBody RateDTO rate) {
		return rateService.createRate(rate);
	}

	@PostMapping("/product/rate/checkExist")
	@PreAuthorize("hasAuthority('roleUSER') or hasAuthority('STAFF') or hasAuthority('roleADMIN')")
	public boolean checkExistRate(@RequestBody RateKey rate) {
		return rateService.checkExist(rate.getProductId(), rate.getCustomerId());
	}

	@Operation(summary = "Create a Rate for Product")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@DeleteMapping("/product/rate/delete/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
	public String deleteRateOfProduct(HttpServletRequest request,@PathVariable(name = "id") int id) {
		String jwt = JwtAuthTokenFilter.parseJwt(request);
		String userId = jwtUtils.getUserNameFromJwtToken(jwt);
		return rateService.deleteRate(id, userId) ? StringUtils.TRUE : StringUtils.FALSE;
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden: Only User can create a review", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@PutMapping("/product/rate/{id}")
	@PreAuthorize("hasRole('USER')")
	public String updateRateOfProduct(@RequestBody RateDTO rate, @PathVariable(name = "id") int id) {
		return rateService.updateRate(rate) ? StringUtils.TRUE : StringUtils.FALSE;
	}
}
