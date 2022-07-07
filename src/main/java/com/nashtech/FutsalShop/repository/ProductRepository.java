package com.nashtech.FutsalShop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.FutsalShop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	List<Product> findByStatusNotAndCategoriesStatusNot(Sort sort, boolean status, boolean cateStatus);

	List<Product> findByCategoriesIdAndStatusNot(Sort sort, int id, boolean status);

	List<Product> findByCategoriesIdAndStatusNot(Pageable pageable, int id, boolean status);

	List<Product> findByNameIgnoreCaseAndStatusNot(String name, boolean status);

	Optional<Product> findByIdIgnoreCase(String id);
	
	Optional<Product> findByIdIgnoreCaseAndStatusNotAndCategoriesStatusNot(String name, boolean status, boolean statusCate);


	int countByCategoriesIdAndStatusNot(int id, boolean status);

	boolean existsByNameAndStatusNot(String name, boolean status);

	@Query("SELECT p FROM Product p "
			+ "WHERE UPPER(p.name) LIKE %?1% and (p.categories.id = ?2) "
			+ "		and p.status != false and p.categories.status != false "
			+ "ORDER BY p.updateDate DESC")
	List<Product> searchProduct(String keyword, int type);

	@Query("SELECT p FROM Product p "
			+ "WHERE UPPER(p.name) LIKE %?1% "
			+ "		and p.status != false and p.categories.status != false "
			+ "ORDER BY p.updateDate DESC")
	List<Product> searchProduct(String keyword);
	
}
