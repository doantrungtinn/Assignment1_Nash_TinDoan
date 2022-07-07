package com.nashtech.FutsalShop.services;

import java.util.List;
import java.util.Optional;

import com.nashtech.FutsalShop.DTO.CategoriesDTO;
import com.nashtech.FutsalShop.model.Categories;

public interface CategoriesService {
	public List<Categories> retrieveCategories();

	public Optional<Categories> getCategories(int id);

	public boolean createCategories(CategoriesDTO categories);

	public boolean deleteCategories(Categories category);

	public boolean checkExistName(int id, String name);

	public boolean updateCategories(CategoriesDTO categories);
}
