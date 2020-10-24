package com.hotel.management.repository;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.management.model.Cart;
import com.hotel.management.model.User;

public interface CartRepository extends JpaRepository<Cart, String>{

	Cart findByUser(User user);
	
}
