package com.hotel.management.controller.panel;
/*
 * created by Manuka Yasas
 * manukayasas99@gmail.com
 * Dhammika-hotel external management
 */

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hotel.management.model.Category;
import com.hotel.management.model.Product;
import com.hotel.management.service.CategoryService;
import com.hotel.management.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@GetMapping("/panel/products")
	public String listProductsByCategory(@RequestParam(value = "categoryID", required = false) String categoryID,
			Model model) {
		
		try {
			if (categoryID != null)
				model.addAttribute("category", categoryService.getCategoryById(categoryID));

			else
				model.addAttribute("products", productService.getAllProducts());
		} catch (Exception e) {
			// TODO: handle exception
		}

		model.addAttribute("categories", categoryService.getAllCategories());

		return "panel/product/products";

	}

	@GetMapping("/panel/products/product")
	public String listProduct(@RequestParam("productID") String productID, Model model) {

		model.addAttribute("product", productService.getProductById(productID));
		model.addAttribute("categories", categoryService.getAllCategories());

		return "panel/product/product";

	}

	@PostMapping("/panel/products/product/addProduct")
	public @ResponseBody boolean AddProduct(@RequestParam("categoryID") String categoryID,
			@RequestParam("name") String name, @RequestParam("description") String description,
			@RequestParam("portion") String portion, @RequestParam("price") String price,
			@RequestParam("tax") String tax, @RequestParam("active") String active,
			@RequestParam(value = "image", required = false) MultipartFile image, Model model) {

		Boolean result = false;

		try {

			Product product = new Product();

			Category category = categoryService.getCategoryById(categoryID);

			if (category != null)
				product.setCategory(category);

			product.setActive(Boolean.parseBoolean(active));
			product.setDescription(description);
			product.setName(name);
			product.setPortion(portion);
			product.setImageData(image.getBytes());
			product.setPrice(Double.parseDouble(price));
			product.setTax(Double.parseDouble(tax));
			result = productService.saveProduct(product);

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;

	}

	@PostMapping("/panel/products/deleteProducts")
	public String deleteProducts(HttpServletRequest request, Model model) {

		String[] fids = {};

		if (request.getParameter("fids[]") != null) {
			fids = request.getParameterValues("fids[]");
		}

		List<String> notDeleted = new ArrayList<>();
		List<String> deletedProducts = new ArrayList<>();

		for (String fid : fids) {

			if (productService.deleteProductById(fid))
				deletedProducts.add(fid);

			else
				notDeleted.add(fid);

		}

		if (deletedProducts.size() != fids.length)
			model.addAttribute("deleteError", true);
		else
			model.addAttribute("deleteSuccess", true);

		model.addAttribute("countDeleted", deletedProducts.size());
		model.addAttribute("countSend", fids.length);
		model.addAttribute("notDeleted", notDeleted);
		model.addAttribute("deletedProducts", deletedProducts);
		model.addAttribute("displayMessage", true);

		return "panel/product/products";

	}

	@PostMapping("/panel/products/product/updateProduct")
	public @ResponseBody boolean updateProduct(@RequestParam("productID") String productID,
			@RequestParam("categoryID") String categoryID, @RequestParam("name") String name,
			@RequestParam("description") String description, @RequestParam("portion") String portion,
			@RequestParam("price") String price, @RequestParam("tax") String tax, @RequestParam("active") String active,
			@RequestParam(value = "image", required = false) MultipartFile image, Model model) {

		Boolean result = false;

		try {

			Product product = productService.getProductById(productID);
			Category category = categoryService.getCategoryById(categoryID);

			if (product != null && category != null) {

				product.setCategory(category);
				product.setActive(Boolean.parseBoolean(active));
				product.setDescription(description);
				product.setName(name);
				product.setPortion(portion);

				if (image != null)
					product.setImageData(image.getBytes());

				product.setPrice(Double.parseDouble(price));
				product.setTax(Double.parseDouble(tax));
				result = productService.saveProduct(product);

			}

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;

	}

	@GetMapping("/panel/products/categories")
	public String listCategory(@RequestParam(value = "categoryID", required = false) String categoryID, Model model) {

		if (categoryID == null)
			model.addAttribute("categories", categoryService.getAllCategories());

		if (categoryID != null) {
			model.addAttribute("categorySent", categoryService.getCategoryById(categoryID));
			System.out.println(categoryService.getCategoryById(categoryID).getName());

		}
		return "panel/product/categories";

	}

	@PostMapping("/panel/products/addCategory")
	public @ResponseBody boolean addCategory(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "description", required = false) String description, Model model) {

		Category category = new Category();

		category.setName(name);
		category.setDescription(description);

		return categoryService.saveCategory(category);
	}

	@PostMapping("/panel/products/deleteCategory")
	public String deleteCategory(HttpServletRequest request, Model model) {

		String[] cids = {};

		if (request.getParameter("cids[]") != null) {
			cids = request.getParameterValues("cids[]");
		}

		List<String> notDeleted = new ArrayList<>();
		List<String> deletedProducts = new ArrayList<>();

		for (String cid : cids) {

			if (categoryService.deleteCategoryById(cid))
				deletedProducts.add(cid);

			else
				notDeleted.add(cid);

		}

		if (deletedProducts.size() != cids.length)
			model.addAttribute("deleteError", true);
		else
			model.addAttribute("deleteSuccess", true);

		model.addAttribute("countDeleted", deletedProducts.size());
		model.addAttribute("countSend", cids.length);
		model.addAttribute("notDeleted", notDeleted);
		model.addAttribute("deletedProducts", deletedProducts);
		model.addAttribute("displayMessage", true);

		return "panel/product/categories";

	}

	@PostMapping("/panel/products/updateCategory")
	public @ResponseBody boolean updateCategory(@RequestParam(value = "categoryID", required = false) String categoryID,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "description", required = false) String description, Model model) {

		boolean result = false;

		try {
			Category category = categoryService.getCategoryById(categoryID);

			if (category != null) {

				category.setName(name);
				category.setDescription(description);
				result = categoryService.saveCategory(category);
			}

		} catch (Exception e) {

			e.printStackTrace();
			result = false;

		}

		return result;
	}

}
