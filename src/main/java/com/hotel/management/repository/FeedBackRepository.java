package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.FeedBack;
import com.hotel.management.model.Product;
import com.hotel.management.model.User;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, String> {

	FeedBack findByFeedbackIDAndUserAndProduct(String id, User user, Product product);
	
}
