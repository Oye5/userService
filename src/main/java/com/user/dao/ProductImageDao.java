package com.user.dao;

import java.util.List;

import com.user.model.ProductImages;


public interface ProductImageDao {

	void updateImageDetails(ProductImages productImages);

	void saveUploadedImage(ProductImages productImages);

	void deleteProductImages(String productId);

	List<ProductImages> getProductImagesByProductId(String productId);
}
