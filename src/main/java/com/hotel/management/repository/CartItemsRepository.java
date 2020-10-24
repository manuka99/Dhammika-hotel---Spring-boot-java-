package com.hotel.management.repository;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.management.model.Cart_Items;

public interface CartItemsRepository extends JpaRepository<Cart_Items, String>{

}
