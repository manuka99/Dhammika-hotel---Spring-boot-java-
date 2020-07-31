package com.hotel.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.model.Category;
import com.hotel.management.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public boolean saveCategory(Category category) {

		boolean result = false;

		try {
			categoryRepository.save(category);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

	@Override
	public Category getCategoryById(String id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public boolean deleteCategoryById(String id) {

		boolean result = false;

		try {
			categoryRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;

	}

}
