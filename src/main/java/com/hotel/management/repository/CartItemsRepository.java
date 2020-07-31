package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.management.model.Cart_Items;

public interface CartItemsRepository extends JpaRepository<Cart_Items, String>{

}
