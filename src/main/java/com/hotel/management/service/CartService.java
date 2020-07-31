package com.hotel.management.service;

import com.hotel.management.model.Cart;
import com.hotel.management.model.User;

public interface CartService {
	boolean saveCart(Cart cart);

	Cart getCartById(String id);

	Cart getCartByUserId(User user);

	boolean deleteCartItem(String id);

	boolean calculateUpdateCartValues(Cart cart);
}
