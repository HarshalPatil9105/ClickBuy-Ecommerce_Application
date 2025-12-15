
package com.clickbuy.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.clickbuy.Exception.ProductException;
import com.clickbuy.model.Product;
import com.clickbuy.model.Seller;
import com.clickbuy.request.CreateProductRequest;

public interface ProductService {

	public Product createProduct(CreateProductRequest req, Seller seller);

	public void deleteProduct(Long productId) throws ProductException;

	public Product updateProduct(Long productId, Product product) throws ProductException;

	Product findProductById(Long productId) throws ProductException;

	List<Product> searchProducts(String query);

	public Page<Product> getAllProducts(String category, String brand, String color, String sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber

	);

	List<Product> getProductBySellerId(Long sellerId);

}
