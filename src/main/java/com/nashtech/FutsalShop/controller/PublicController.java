package com.nashtech.FutsalShop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.FutsalShop.DTO.ProductDTO;
import com.nashtech.FutsalShop.model.Categories;
import com.nashtech.FutsalShop.model.Product;
import com.nashtech.FutsalShop.model.Rate;
import com.nashtech.FutsalShop.exception.ObjectNotFoundException;
import com.nashtech.FutsalShop.services.CategoriesService;
import com.nashtech.FutsalShop.services.ProductService;
import com.nashtech.FutsalShop.services.RateService;
import com.nashtech.FutsalShop.services.ReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
	@Autowired
	private ProductService productService;

	@Autowired
	CategoriesService cateService;

	@Autowired
	RateService rateService;
	
	@Autowired
	ReportService reportService;

	@Operation(summary = "Get all Categories") // CATEGORIES
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Categories.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/categories")
	public List<Categories> retrieveCategories() {
		return cateService.retrieveCategories();
	}

	@Operation(summary = "Get all Product Infomation") // PRODUCT
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/product")
	public List<Product> retrieveProducts() {
		return productService.retrieveProducts();
	}

	@GetMapping("/product/search")
	public List<Product> searchProducts(@RequestParam(name = "keyword") String keyword,
										@RequestParam(name = "type", required = false) Integer type) {
		if (type != null)
			return productService.searchProductByType(keyword, type);
		else
			return productService.searchProduct(keyword);
	}

	@GetMapping("/product/{cateId}")
	public List<Product> retrieveProductsByType(@PathVariable(name = "cateId") int id) {
		return productService.retrieveProductsByType(id);
	}
	
	@GetMapping("/product/hotProd/{size}")
	public List<Product> getHotProducts(@PathVariable(name = "size") int size) {
		return reportService.hotProduct(size);
	}

	@Operation(summary = "Get a Product Infomation by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/product/search/{id}")
	public Product getProduct(@PathVariable(name = "id") String id) {
		return productService.getProduct(id)
				.orElseThrow(() -> new ObjectNotFoundException("Could not find product with Id: " + id));
	}

	@Operation(summary = "Get Product by Type for Page")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/product/page")
	public List<Product> getProductPage(@RequestParam(name = "pagenum") int page,
										@RequestParam(name = "size") int size, @RequestParam(name = "type") int id) {
		return productService.getProductPage(page, size, id);
	}

	@GetMapping("/productDTO/page")
	public List<ProductDTO> getProductDTOPage(@RequestParam(name = "pagenum") int page,
			@RequestParam(name = "size") int size, @RequestParam(name = "type") int id) {
		return productService.getProductPage(page, size, id).stream().map(productService::convertToDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("/productSort/page")
	public List<Product> getProductSortPage(@RequestParam(name = "pagenum") int page,
											@RequestParam(name = "size") int size, @RequestParam(name = "type") int id,
											@RequestParam(name = "sort") String sort) {
		return productService.getProductPageWithSort(page, size, id, sort);
	}

	@GetMapping("/product/numTotal/{id}")
	public int getNumTotalProductByCategories(@PathVariable(name = "id") int id) {
		return productService.getNumProductByCategories(id);
	}

	@Operation(summary = "Get Top Product by Type for Welcome Page")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/product/categories/{id}")
	public List<Product> getNewstProductByCategories(@PathVariable(name = "id") int id) {
		return productService.getNewestProductCategories(id, 4);
	}

	@Operation(summary = "Get number of Rate for Product") // RATE OF PRODUCT
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/product/rateTotal/{id}")
	public int getRateOfProduct(@PathVariable(name = "id") String id) {
		return rateService.getNumRate(id);
	}

	@GetMapping("/product/rateAvgProd/{id}")
	public double getAverageRateOfProduct(@PathVariable(name = "id") String id) {
		return rateService.getAverageRateNumByProduct(id);
	}

	@Operation(summary = "Get a Rate for Product by Pages")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The request has succeeded", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized, Need to login first!", content = @Content),
			@ApiResponse(responseCode = "400", description = "Bad Request: Invalid syntax", content = @Content),
			@ApiResponse(responseCode = "404", description = "Can not find the requested resource", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content) })
	@GetMapping("/product/rate")
	public List<Rate> getRateProductPages(@RequestParam(name = "pagenum") int page,
										  @RequestParam(name = "size") int size, @RequestParam(name = "id") String id) {
		return rateService.getRateProductPage(id, page, size);
	}
}
