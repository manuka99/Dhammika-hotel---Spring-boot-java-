package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.management.model.EmailConfirmToken;
import com.hotel.management.model.User;

@Repository
public interface EmailConfirmTokenRepository extends JpaRepository<EmailConfirmToken, String> {

	EmailConfirmToken findByUser(User user);
	
}
