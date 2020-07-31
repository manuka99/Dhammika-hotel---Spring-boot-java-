package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.management.model.Cart;
import com.hotel.management.model.User;

public interface CartRepository extends JpaRepository<Cart, String>{

	Cart findByUser(User user);
	
}
