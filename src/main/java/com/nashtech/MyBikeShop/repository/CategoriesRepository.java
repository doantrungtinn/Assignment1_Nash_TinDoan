package com.nashtech.MyBikeShop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.MyBikeShop.model.categories;

@Repository
public interface CategoriesRepository extends JpaRepository<categories, Integer> {
	List<categories> findByStatusNot(Sort sort, boolean status);
	
	Optional<categories> findByIdAndStatusNot(int id, boolean status);
	
	Boolean existsByNameAndStatusNot(String name, boolean status);

	List<categories> findByNameIgnoreCaseAndStatusNot(String name, boolean status);

	int countByNameAndStatusNot(String name, boolean status);
}
