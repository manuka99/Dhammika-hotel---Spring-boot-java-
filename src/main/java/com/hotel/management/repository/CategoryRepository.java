package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}
