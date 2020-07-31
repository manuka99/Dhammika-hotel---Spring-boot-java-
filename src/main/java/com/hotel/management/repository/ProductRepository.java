package com.hotel.management.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	@Query("select pt from Product pt where pt.category.categoryID = :idCat and pt.price > :price")
	org.springframework.data.domain.Page<Product> findByCategoryAndPrice(@Param("idCat") String idCat,
			@Param("price") double price, Pageable pageable);

	// org.springframework.data.domain.Page<Product> findByCategoryAndPrice(Category
	// category, String price, Pageable pageable);

	@Query("select pt from Product pt where pt.price > :price")
	org.springframework.data.domain.Page<Product> findAllAndByPrice(@Param("price") double price, Pageable pageable);

}
