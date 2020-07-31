package com.hotel.management.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hotel.management.model.Product;

public interface ProductService {
	
	List<Product> getAllProducts();
	boolean saveProduct(Product product);
	Product getProductById(String id);
	boolean deleteProductById(String id);
	Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String catergoryID, String price);
	
}
