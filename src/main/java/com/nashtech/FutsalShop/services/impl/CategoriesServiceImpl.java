package com.nashtech.FutsalShop.services.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nashtech.FutsalShop.DTO.CategoriesDTO;
import com.nashtech.FutsalShop.model.Categories;
import com.nashtech.FutsalShop.exception.ObjectAlreadyExistException;
import com.nashtech.FutsalShop.repository.CategoriesRepository;
import com.nashtech.FutsalShop.services.CategoriesService;
import com.nashtech.FutsalShop.services.ProductService;

@Service
public class CategoriesServiceImpl implements CategoriesService {
	@Autowired
	CategoriesRepository categoriesRepository;

	@Autowired
	ProductService productService;

	public List<Categories> retrieveCategories() {
		Sort sortable = Sort.by("id").ascending();
		return categoriesRepository.findByStatusNot(sortable,false);
	}

	public Optional<Categories> getCategories(int id) {
		return categoriesRepository.findByIdAndStatusNot(id, false);
	}

	public boolean createCategories(CategoriesDTO categoriesDTO) {
		try {
			boolean checkName = checkExistName(0, categoriesDTO.getName());
			if (checkName) {
				Categories categoriesConvert = new Categories(categoriesDTO);
				categoriesConvert.setStatus(true);
				categoriesRepository.save(categoriesConvert);
				return true;
			} else
				throw new ObjectAlreadyExistException("There is a category with the same Name");
		} catch (IllegalArgumentException | ConstraintViolationException ex) {
			return false;
		}

	}

	public boolean checkExistName(int id, String name) {
		List<Categories> cateList = categoriesRepository.findByNameIgnoreCaseAndStatusNot(name, false);
		if (cateList.isEmpty())
			return true;
		else if ((cateList.size() > 1) || ((cateList.size() == 1) && (cateList.get(0).getId() != id)))
			return false;
		else
			return true;
	}

	public boolean deleteCategories(Categories category) {
//		categoriesRepository.deleteById(id);
		category.setStatus(false);
		categoriesRepository.save(category);
		return true;
		
	}

	public boolean updateCategories(CategoriesDTO categoriesDTO) {
		try {
			boolean check = checkExistName(categoriesDTO.getId(), categoriesDTO.getName());
			if (check) {
				Categories categoriesConvert = new Categories(categoriesDTO);
				categoriesConvert.setStatus(true);
				categoriesRepository.save(categoriesConvert);
				return true;
			} else
				throw new ObjectAlreadyExistException("There is a category with the same Name");
		} catch (IllegalArgumentException | ConstraintViolationException ex) {
			return false;
		}
	}
}
