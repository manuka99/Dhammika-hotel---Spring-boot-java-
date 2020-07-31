package com.hotel.management.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotel.management.model.Category;
import com.hotel.management.model.CurrentUser;
import com.hotel.management.model.Product;
import com.hotel.management.model.User;
import com.hotel.management.service.CategoryService;
import com.hotel.management.service.CurrencyGeneratorService;
import com.hotel.management.service.OrderService;
import com.hotel.management.service.ProductService;

@Controller
public class FoodController {

	private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CurrencyGeneratorService currencyGeneratorService;

	@GetMapping("/menu")
	public String menu(Model model) {
		return findPaginatedProducts(1, "name", "asc", "null", "0", model);

	}

	@GetMapping("/newFood")
	public String newFood(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "upload";

	}

	@GetMapping("/product")
	public String viewProduct(@RequestParam(value = "productID") String id, Model model) {
		Product product = productService.getProductById(id);
		model.addAttribute("product", product);
		model.addAttribute("canReview", orderService.hasProductOfUserOrder(product, getPrincipalUser()));
		model.addAttribute("userReview", getPrincipalUser());
		model.addAttribute("usd", currencyGeneratorService.priceOfaUsdToLkr());
		return "products/product";

	}

	@GetMapping("menu/page/{pageNo}")
	public String findPaginatedProducts(@PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir,
			@RequestParam("catergoryID") String catergoryID, @RequestParam("price") String price, Model model) {
		int pageSize = 5;
		Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir, catergoryID, price);
		List<Product> listProducts = page.getContent();

		List<Category> ListCategory = categoryService.getAllCategories();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("catergoryID", catergoryID);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("price", price);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listProducts", listProducts);
		model.addAttribute("ListCategory", ListCategory);
		model.addAttribute("usd", currencyGeneratorService.priceOfaUsdToLkr());
		return "products/menu";
	}

	public User getPrincipalUser() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User();

		if (principal instanceof CurrentUser) {
			user = ((CurrentUser) principal).getUser();
		}

		return user;
	}

}
