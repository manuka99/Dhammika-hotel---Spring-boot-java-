package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.List;

import com.hotel.management.model.Category;

public interface CategoryService {

	List<Category> getAllCategories();
	boolean saveCategory(Category category);
	Category getCategoryById(String id);
	boolean deleteCategoryById(String id);
	
}
