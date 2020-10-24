package com.hotel.management.controller;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.management.model.Cart;
import com.hotel.management.model.Cart_Items;
import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.Product;
import com.hotel.management.model.User;
import com.hotel.management.service.CartService;
import com.hotel.management.service.CurrencyGeneratorService;
import com.hotel.management.service.ProductService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CurrencyGeneratorService currencyGeneratorService;

	private Logger logger = LoggerFactory.getLogger(CartController.class);

	@GetMapping("/user/cart")
	public String UserCart(Model model) {

		boolean result = cartService.calculateUpdateCartValues(getUserCart());

		if (result) {

			try {
				Cart cart = cartService.getCartById(getUserCart().getCartID());
				model.addAttribute("cart", cart);
				model.addAttribute("usd", currencyGeneratorService.priceOfaUsdToLkr());
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("exception");
			}

		}

		return "member/cart";
	}

	@GetMapping("/user/addToCart")
	@ResponseBody
	public String UserCartAdd(Model model, @RequestParam("productID") String productID) throws NullPointerException {

		boolean updated = false;
		boolean canAddToCart = true;
		boolean addToCart = false;
		String msg = "failed";
		int intQuantity = 1;
		boolean result = false;
		/*
		 * validate quantity
		 */

		Cart cart = getUserCart();

		Set<Cart_Items> set = cart.getCart_Items();

		try {

			Product product = productService.getProductById(productID);

			if (cart != null && product.isActive() && intQuantity < 4 && intQuantity > 0) {

				for (Cart_Items cart_item : set) {

					/*
					 * update
					 */
					if (cart_item.getProduct().getProductID().equals(productID)
							&& (intQuantity + cart_item.getQuantity()) < 4) {

						cart_item.setQuantity(intQuantity + cart_item.getQuantity());
						updated = true;
						// update

					}

					else if (cart_item.getProduct().getProductID().equals(productID))
						canAddToCart = false;

				}

				if (updated == false && canAddToCart) {
					Cart_Items cart_item = new Cart_Items();
					cart_item.setProduct(product);
					cart_item.setQuantity(intQuantity);
					cart_item.setCart(cart);
					set.add(cart_item);
					addToCart = true;
					// added
				}

				cart.setCart_Items(set);
				result = cartService.calculateUpdateCartValues(cart);

			}

			if (updated && result)
				msg = "updated";

			if (addToCart && result)
				msg = "added";

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("exception");
		}
		return msg;
	}

	@GetMapping("/user/cartUpdate")
	@ResponseBody
	public String UserCartUpdate(Model model, @RequestParam("productID") String productID,
			@RequestParam("quantity") String quantity) {

		boolean result = false;
		String msg = "failed";

		Cart cart = getUserCart();

		Cart_Items updateItem = new Cart_Items();

		if (cart != null) {

			for (Cart_Items cart_item : cart.getCart_Items()) {

				try {

					if (cart_item.getProduct().getProductID().equals(productID) && cart_item.getProduct().isActive()
							&& Integer.parseInt(quantity) < 4 && Integer.parseInt(quantity) > 0) {

						updateItem = cart_item;
					}

				} catch (Exception e) {
					e.printStackTrace();
					logger.info("exception");
				}

			}

			if (updateItem.getId() != null) {

				cart.getCart_Items().remove(updateItem);
				updateItem.setQuantity(Integer.parseInt(quantity));
				cart.getCart_Items().add(updateItem);
				result = cartService.calculateUpdateCartValues(cart);

			}

		}

		if (result)
			msg = "updated";

		return msg;
	}

	@GetMapping("/user/cartRemove")
	@ResponseBody
	public boolean UserCartRemove(Model model, @RequestParam("pid") String productID) {

		boolean result = false;

		Cart_Items deleteItem = new Cart_Items();

		Cart cart = getUserCart();

		if (cart != null) {

			for (Cart_Items cart_item : cart.getCart_Items()) {

				if (cart_item.getProduct().getProductID().equals(productID))
					deleteItem = cart_item;
			}

			if (deleteItem.getId() != null) {

				try {

					result = cart.getCart_Items().remove(deleteItem);

					if (result)
						result = cartService.saveCart(cart);

				} catch (Exception e) {
					e.printStackTrace();
					logger.info("exception");
				}

			}

		}

		return result;
	}

	@GetMapping("/user/cartSize")
	@ResponseBody
	public int UserCartSize(Model model) {

		return getUserCart().getCart_Items().size();
	}

	public Cart getUserCart() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = new User();

		if (principal instanceof CurrentUser) {
			user = ((CurrentUser) principal).getUser();
		}

		Cart cart = new Cart();

		try {
			cart = cartService.getCartByUserId(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("exception at cart");
		}

		return cart;

	}

}
