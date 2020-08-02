package com.hotel.management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.management.controller.ComplainsController;
import com.hotel.management.model.Cart;
import com.hotel.management.model.Cart_Items;
import com.hotel.management.model.User;
import com.hotel.management.repository.CartItemsRepository;
import com.hotel.management.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {
	
	private Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemsRepository cartItemRepository;
	
	@Autowired
	private DeliveryFeeService deliveryFeeService;

	@Override
	public boolean saveCart(Cart cart) {

		boolean result = false;

		try {
			cartRepository.save(cart);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;

	}

	@Override
	public Cart getCartById(String id) {
		return cartRepository.findById(id).get();
	}

	@Override
	public Cart getCartByUserId(User user) {
		return cartRepository.findByUser(user);
	}

	@Override
	public boolean deleteCartItem(String id) {

		boolean result = false;

		try {
			cartItemRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;

	}
	
	
	public boolean calculateUpdateCartValues(Cart cart) {

		double tax = 0;
		double product_price_total = 0;
		double shipping_fee = 0;
		double total = 0;
		int itemCount = 0;

		for (Cart_Items cart_items : cart.getCart_Items()) {

			try {
				tax += cart_items.getProduct().getTax() * cart_items.getQuantity();
				product_price_total += cart_items.getProduct().getPrice() * cart_items.getQuantity();
				++itemCount;
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		shipping_fee = deliveryFeeService.getShippingFeeFromSubTotal(product_price_total);
		total = tax + product_price_total + shipping_fee;

		cart.setItemCount(itemCount);
		cart.setTax(tax);
		cart.setProductPriceTotal(product_price_total);
		cart.setShippingFee(shipping_fee);
		cart.setTotal(total);

		return saveCart(cart);
	}

}
