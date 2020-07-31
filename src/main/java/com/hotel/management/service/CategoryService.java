package com.hotel.management.service;

import java.util.List;

import com.hotel.management.model.Category;

public interface CategoryService {

	List<Category> getAllCategories();
	boolean saveCategory(Category category);
	Category getCategoryById(String id);
	boolean deleteCategoryById(String id);
	
}
