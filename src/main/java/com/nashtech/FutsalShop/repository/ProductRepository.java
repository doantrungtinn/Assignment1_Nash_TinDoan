package com.nashtech.FutsalShop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nashtech.FutsalShop.model.product;

@Repository
public interface ProductRepository extends JpaRepository<product, String> {
	List<product> findByStatusNotAndCategoriesStatusNot(Sort sort, boolean status, boolean cateStatus);

	List<product> findByCategoriesIdAndStatusNot(Sort sort, int id, boolean status);

	List<product> findByCategoriesIdAndStatusNot(Pageable pageable, int id, boolean status);

	List<product> findByNameIgnoreCaseAndStatusNot(String name, boolean status);

	Optional<product> findByIdIgnoreCase(String id);
	
	Optional<product> findByIdIgnoreCaseAndStatusNotAndCategoriesStatusNot(String name, boolean status, boolean statusCate);


	int countByCategoriesIdAndStatusNot(int id, boolean status);

	boolean existsByNameAndStatusNot(String name, boolean status);

	@Query("SELECT p FROM product p "
			+ "WHERE UPPER(p.name) LIKE %?1% and (p.categories.id = ?2) "
			+ "		and p.status != false and p.categories.status != false "
			+ "ORDER BY p.updateDate DESC")
	List<product> searchProduct(String keyword, int type);

	@Query("SELECT p FROM product p "
			+ "WHERE UPPER(p.name) LIKE %?1% "
			+ "		and p.status != false and p.categories.status != false "
			+ "ORDER BY p.updateDate DESC")
	List<product> searchProduct(String keyword);
	
}
