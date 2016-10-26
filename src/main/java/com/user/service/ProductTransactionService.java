package com.user.service;

import java.util.List;

import com.user.model.ProductTransaction;

public interface ProductTransactionService {

	String saveBuyingDetails(ProductTransaction productTransaction);

	void deleteProductTransaction(String productId);

	List<ProductTransaction> getBuyingProductByUserId(String userId);

	List<ProductTransaction> getSoldProductByUserId(String userId);
}
