package com.nashtech.MyBikeShop.services;

import java.util.List;
import java.util.Optional;

import com.nashtech.MyBikeShop.DTO.CategoriesDTO;
import com.nashtech.MyBikeShop.model.categories;

public interface CategoriesService {
	public List<categories> retrieveCategories();

	public Optional<categories> getCategories(int id);

	public boolean createCategories(CategoriesDTO categories);

	public boolean deleteCategories(categories category);

	public boolean checkExistName(int id, String name);

	public boolean updateCategories(CategoriesDTO categories);
}
