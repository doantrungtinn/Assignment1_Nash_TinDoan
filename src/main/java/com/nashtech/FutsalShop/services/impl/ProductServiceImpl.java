package com.nashtech.FutsalShop.services.impl;

import java.time.LocalDateTime;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nashtech.FutsalShop.DTO.ProductDTO;
import com.nashtech.FutsalShop.model.Categories;
import com.nashtech.FutsalShop.model.Person;
import com.nashtech.FutsalShop.model.Product;
import com.nashtech.FutsalShop.exception.ObjectAlreadyExistException;
import com.nashtech.FutsalShop.exception.ObjectNotFoundException;
import com.nashtech.FutsalShop.exception.ObjectPropertiesIllegalException;
import com.nashtech.FutsalShop.repository.ProductRepository;
import com.nashtech.FutsalShop.services.CategoriesService;
import com.nashtech.FutsalShop.services.OrderService;
import com.nashtech.FutsalShop.services.PersonService;
import com.nashtech.FutsalShop.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	ModelMapper mapper;

	@Autowired
	OrderService orderService;

	@Autowired
	CategoriesService cateService;

	@Autowired
	PersonService personService;

	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

	public ProductServiceImpl() {
		super();
	}

	public List<Product> retrieveProducts() {
		Sort sortable = Sort.by("id").ascending();
		return productRepository.findByStatusNotAndCategoriesStatusNot(sortable, false, false);
	}

	public ProductDTO convertToDTO(Product product) {
		ProductDTO productDTO = mapper.map(product, ProductDTO.class);
		productDTO.setNameEmployeeUpdate(product.getEmployeeUpdate().getFullname());
		return productDTO;
	}

	public List<Product> retrieveProductsByType(int categoriesId) {
		Sort sortable = Sort.by("updateDate").descending();
		return productRepository.findByCategoriesIdAndStatusNot(sortable, categoriesId, false);
	}

	public List<Product> getProductPage(int page, int size, int categoriesId) {
		Sort sortable = Sort.by("updateDate").descending();
		Pageable pageable = PageRequest.of(page, size, sortable);
		return productRepository.findByCategoriesIdAndStatusNot(pageable, categoriesId, false);
	}

	public List<Product> getNewestProductCategories(int categoriesId, int size) {
		Sort sortable = Sort.by("updateDate").descending().and(Sort.by("quantity").descending());
		Pageable pageable = PageRequest.of(0, size, sortable);
		return productRepository.findByCategoriesIdAndStatusNot(pageable, categoriesId, false);
	}

	public List<Product> getProductPageWithSort(int page, int size, int categoriesId, String sortType) {

		if (sortType.equalsIgnoreCase("ASC")) {
			Sort sortable = Sort.by("price").ascending();
			Pageable pageable = PageRequest.of(page, size, sortable);
			return productRepository.findByCategoriesIdAndStatusNot(pageable, categoriesId, false);
		} else {
			Sort sortable = Sort.by("price").descending();
			Pageable pageable = PageRequest.of(page, size, sortable);
			return productRepository.findByCategoriesIdAndStatusNot(pageable, categoriesId, false);
		}

	}

	public Optional<Product> getProduct(String id) {
		return productRepository.findByIdIgnoreCaseAndStatusNotAndCategoriesStatusNot(id, false, false);
	}

	public Optional<Product> getProductInludeDeleted(String id) {
		return productRepository.findByIdIgnoreCase(id);
	}

	public List<Product> searchProduct(String keyword) {
		return productRepository.searchProduct(keyword.toUpperCase());
	}

	public List<Product> searchProductByType(String keyword, int type) {
		return productRepository.searchProduct(keyword.toUpperCase(), type);
	}

	public Product createProduct(ProductDTO productDTO, int id) {
		
		try {
			boolean checkName = checkExistName(productDTO.getName());
			boolean checkId = checkExistId(productDTO.getId());
			if (!checkId) {
				logger.error(
						"Account id " + id + " create product " + productDTO.getId() + " failed: ID had been used");
				throw new ObjectAlreadyExistException("Error: Product's ID had been used.");
			} else if (!checkName) {
				logger.error(
						"Account id " + id + " create product " + productDTO.getId() + " failed: ID had been used");
				throw new ObjectAlreadyExistException("Error: Product's name had been used=");
			} else {
				Categories cate = cateService.getCategories(productDTO.getCategoriesId()).get();
				Person employee = personService.getPerson(id).get();
				Product productEntity = new Product(productDTO, cate);
				productEntity.setCreateDate(LocalDateTime.now());
				productEntity.setUpdateDate(LocalDateTime.now());
				productEntity.setStatus(true);
				productEntity.setEmployeeUpdate(employee);
				productEntity.setPhoto(productDTO.getPhoto());
				logger.info("Account id " + id + " create product id " + productDTO.getId() + " success");
				return productRepository.save(productEntity);
			}
		} catch (DataAccessException ex) {
			logger.error("Account id " + id + " create product " + productDTO.getId() + " failed");
			logger.error(ex.getMessage());
			throw new ObjectNotFoundException("Failed!" + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			logger.error("Account id " + id + " create product " + productDTO.getId() + " failed");
			logger.error(ex.getMessage());
			throw new ObjectPropertiesIllegalException("Failed!" + ex.getMessage());
		} catch (NoSuchElementException ex) {
			logger.error("Account id " + id + " create product " + productDTO.getId()
					+ " failed: No found user with ID " + id);
			throw new ObjectNotFoundException("Error: No found account with ID " + id);
		}
	}

	public boolean deleteProduct(String id, int userId) {
		Product prod;
		Person person;
		try {
			prod = getProduct(id).get();
		} catch (NoSuchElementException ex) {
			logger.error(
					"Account id " + userId + " delete product id " + id + " failed: No found product with ID " + id);
			throw new ObjectNotFoundException("Error: No found product with ID " + id);
		}
		try {
			person = personService.getPerson(userId).get();
		} catch (NoSuchElementException ex) {
			logger.error(
					"Account id " + userId + " delete product id " + id + " failed: No found account with ID " + id);
			throw new ObjectNotFoundException("Error: No found account with ID " + id);
		}
//		person.getProduct().remove(prod);
//		productRepository.deleteById(id);
		prod.setUpdateDate(LocalDateTime.now());
		prod.setEmployeeUpdate(person);
		prod.setStatus(false);
		productRepository.save(prod);
		logger.info("Account id " + userId + " delete product id " + id + " success");
		return true;
//		} catch (NoSuchElementException ex) {
//			logger.error("Account id " + userId + " delete product id " + id + " failed: No found account with ID " + id);
//			throw new ObjectNotFoundException("Error: No found account with ID " + id);
//		}
	}

	public boolean updateProduct(ProductDTO productDTO, int userId) {
		try {
			boolean check = checkExistNameUpdate(productDTO.getId(), productDTO.getName());
			if (check) {
				Categories cate = cateService.getCategories(productDTO.getCategoriesId()).get();
				Person employee = personService.getPerson(userId).get();
				Product product = new Product(productDTO, cate);
				product.setEmployeeUpdate(employee);
				product.setStatus(true);
				productRepository.save(updateDate(product));
				logger.info("Account id " + userId + " delete product id " + productDTO.getId() + " success");
				return true;
			} else
				logger.error(
						"Account id " + userId + " update product " + productDTO.getId() + " failed: Duplicate name");
			throw new ObjectAlreadyExistException("Error: Product's name had been used");
		} catch (NoSuchElementException ex) {
			logger.error("Account id " + userId + " update product id " + productDTO.getId()
					+ " failed: No found account with ID " + userId);
			throw new ObjectNotFoundException("Error: No found account with ID " + userId);
		}
	}

	public boolean updateProductQuantity(String id, int numberChange) {
		try {
			Product product = getProduct(id).get();
			if (product.getQuantity() - Math.abs(numberChange) < 0) {
				throw new ObjectPropertiesIllegalException("Quantity of product is not enough to update");
			} else if (!product.isStatus()) {
				logger.error("Update product quantity " + id + " failed: Product not found");
				throw new ObjectNotFoundException("Product not found with id " + id);
			}
			product.changeQuantity(numberChange);
			productRepository.save(updateDate(product));
			return true;
		} catch (NoSuchElementException ex) {
			logger.error("Update product quantity " + id + " failed: Product not found");
			return false;
		}
	}

	public boolean updateProductQuantityToCancel(String id, int numberChange) {
		try {
			Product product = productRepository.findByIdIgnoreCase(id).get();
			if (product.getQuantity() - Math.abs(numberChange) < 0) {
				throw new ObjectPropertiesIllegalException("Quantity of product is not enough to update");
			}
			product.changeQuantity(numberChange);
			productRepository.save(updateDate(product));
			return true;
		} catch (NoSuchElementException ex) {
			logger.error("Update product quantity " + id + " failed: Product not found");
			ex.printStackTrace();
			return false;
		}
	}

	public List<Product> findProductByCategories(int id) {
		Sort sortable = Sort.by("updateDate").descending();
		return productRepository.findByCategoriesIdAndStatusNot(sortable, id, false);
	}

	public Product updateDate(Product product) {
		product.setUpdateDate(LocalDateTime.now());
		return product;
	}

	public int getNumProductByCategories(int id) {
		return productRepository.countByCategoriesIdAndStatusNot(id, false);
	}

	public boolean checkExistNameUpdate(String id, String name) {
		List<Product> prodList = productRepository.findByNameIgnoreCaseAndStatusNot(name, false);
		if (prodList.isEmpty())
			return true;
		else if ((prodList.size() > 1) || ((prodList.size() == 1) && (!prodList.get(0).getId().equalsIgnoreCase(id))))
			return false;
		else
			return true;
	}

	public boolean checkExistId(String id) {
		Optional<Product> prod = productRepository.findByIdIgnoreCase(id);
		return prod.isEmpty();
	}

	public boolean checkExistName(String name) {
		List<Product> prodList = productRepository.findByNameIgnoreCaseAndStatusNot(name, false);
		return prodList.isEmpty();
	}

	@Override
	public Product updateProductWithoutCheckAnything(Product product) {
		return productRepository.save(product);
	}
}
