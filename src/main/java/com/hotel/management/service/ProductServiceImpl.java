package com.hotel.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotel.management.model.Category;
import com.hotel.management.model.Product;
import com.hotel.management.repository.CategoryRepository;
import com.hotel.management.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public boolean saveProduct(Product product) {

		boolean result = false;

		try {
			productRepository.save(product);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;

	}

	@Override
	public Product getProductById(String id) {
		Optional<Product> optional = productRepository.findById(id);
		Product product = null;
		if (optional.isPresent()) {
			product = optional.get();
		} else {
			throw new RuntimeException(" Product not found for id :: " + id);
		}

		return product;
	}

	@Override
	public boolean deleteProductById(String id) {

		boolean result = false;

		try {
			productRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception

			result = false;
		}

		return result;
	}

	@Override
	public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
			String catergoryID, String price) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

		Page<Product> paginatedProduct = null;

		Category catergory = new Category();

		try {
			catergory = categoryRepository.findById(catergoryID).get();
		} catch (Exception e) {
			catergory = new Category();
		}

		double dPrice = 0;

		try {

			dPrice = Double.parseDouble(price);

		} catch (Exception e) {
			dPrice = 0;
		}

		if (catergory.getCategoryID() != null)
			paginatedProduct = productRepository.findByCategoryAndPrice(catergory.getCategoryID(), dPrice, pageable);

		else
			paginatedProduct = productRepository.findAllAndByPrice(dPrice, pageable);
		// paginatedProduct = productRepository.findAll(pageable);

		return paginatedProduct;
	}

}
