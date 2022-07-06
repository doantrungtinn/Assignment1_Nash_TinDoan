package com.nashtech.FutsalShop.services;

import java.util.List;
import java.util.Optional;

import com.nashtech.FutsalShop.DTO.ProductDTO;
import com.nashtech.FutsalShop.model.product;

public interface ProductService {
	public List<product> retrieveProducts();
	
	public List<product> retrieveProductsByType(int categoriesId);

	public List<product> getProductPage(int num, int size, int categoriesId);

	public List<product> getProductPageWithSort(int num, int size, int categoriesId, String sortType);

	public List<product> getNewestProductCategories(int idCategrories, int size);
	
	public List<product> searchProduct(String keyword);
	
	public List<product> searchProductByType(String keyword, int type);

	public Optional<product> getProduct(String id);
	
	public Optional<product> getProductInludeDeleted(String id);

	public product createProduct(ProductDTO product, int id);

	public boolean deleteProduct(String id, int userId);

	public boolean updateProduct(ProductDTO product, int id);

	public boolean updateProductQuantity(String id, int numberChange);
	
	public boolean updateProductQuantityToCancel(String id, int numberChange);

	public List<product> findProductByCategories(int id);

	public product updateDate(product product);

	public int getNumProductByCategories(int id);

	public boolean checkExistNameUpdate(String id, String name);

	public boolean checkExistName(String name);

	public boolean checkExistId(String id);
	
	public product updateProductWithoutCheckAnything (product product);
	
	public ProductDTO convertToDTO(product product);
}
