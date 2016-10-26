package com.user.dao;

import java.util.List;

import com.user.model.ProductTransaction;

public interface ProductTransactionDao {

	String saveBuyingDetails(ProductTransaction productTransaction);

	void deleteProductTransaction(String productId);

	List<ProductTransaction> getBuyingProductByUserId(String userId);

	List<ProductTransaction> getSoldProductByUserId(String userId);
}
