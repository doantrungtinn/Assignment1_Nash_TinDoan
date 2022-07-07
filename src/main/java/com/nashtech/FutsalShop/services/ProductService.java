package com.nashtech.FutsalShop.services;

import java.util.List;
import java.util.Optional;

import com.nashtech.FutsalShop.DTO.ProductDTO;
import com.nashtech.FutsalShop.model.Product;

public interface ProductService {
	public List<Product> retrieveProducts();
	
	public List<Product> retrieveProductsByType(int categoriesId);

	public List<Product> getProductPage(int num, int size, int categoriesId);

	public List<Product> getProductPageWithSort(int num, int size, int categoriesId, String sortType);

	public List<Product> getNewestProductCategories(int idCategrories, int size);
	
	public List<Product> searchProduct(String keyword);
	
	public List<Product> searchProductByType(String keyword, int type);

	public Optional<Product> getProduct(String id);
	
	public Optional<Product> getProductInludeDeleted(String id);

	public Product createProduct(ProductDTO product, int id);

	public boolean deleteProduct(String id, int userId);

	public boolean updateProduct(ProductDTO product, int id);

	public boolean updateProductQuantity(String id, int numberChange);
	
	public boolean updateProductQuantityToCancel(String id, int numberChange);

	public List<Product> findProductByCategories(int id);

	public Product updateDate(Product product);

	public int getNumProductByCategories(int id);

	public boolean checkExistNameUpdate(String id, String name);

	public boolean checkExistName(String name);

	public boolean checkExistId(String id);
	
	public Product updateProductWithoutCheckAnything (Product product);
	
	public ProductDTO convertToDTO(Product product);
}
