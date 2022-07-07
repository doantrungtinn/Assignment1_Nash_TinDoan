package com.nashtech.FutsalShop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.FutsalShop.model.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
	List<Categories> findByStatusNot(Sort sort, boolean status);
	
	Optional<Categories> findByIdAndStatusNot(int id, boolean status);
	
	Boolean existsByNameAndStatusNot(String name, boolean status);

	List<Categories> findByNameIgnoreCaseAndStatusNot(String name, boolean status);

	int countByNameAndStatusNot(String name, boolean status);
}
