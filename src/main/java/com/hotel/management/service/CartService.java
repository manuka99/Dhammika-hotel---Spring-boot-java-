package com.hotel.management.service;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import com.hotel.management.model.Cart;
import com.hotel.management.model.User;

public interface CartService {
	boolean saveCart(Cart cart);

	Cart getCartById(String id);

	Cart getCartByUserId(User user);

	boolean deleteCartItem(String id);

	boolean calculateUpdateCartValues(Cart cart);
}
