package com.user.service;

import java.util.List;

import com.user.model.ProductImages;


public interface ProductImageService {

	void updateImageDetails(ProductImages productImages);

	void saveUploadedImage(ProductImages productImages);

	void deleteProductImages(String productId);

	List<ProductImages> getProductImagesByProductId(String productId);

}