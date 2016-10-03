package com.user.dao;

import java.util.List;

import com.user.model.Product;

public interface ProductDao {

	String saveProduct(Product product);

	void updateProduct(Product product);

	List<Product> findAllProducts();

	void deleteProduct(Product product);

	List<Product> getProductByProductId(String productId);

	List<Product> getProductsByCategoryId(int categoryId);

	List<Product> getProductByUserId(String sellerId);

}
