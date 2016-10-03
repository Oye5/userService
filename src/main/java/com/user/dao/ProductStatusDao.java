package com.user.dao;

import java.util.List;

import com.user.model.ProductStatus;


public interface ProductStatusDao {

	ProductStatus getProductStatus(String productId);

	void deleteProductStatus(String productId);

	void saveProductStatus(ProductStatus productStatus);

	List<ProductStatus> getFavouriteProducts();

	void updateProductStatus(ProductStatus status);
}
