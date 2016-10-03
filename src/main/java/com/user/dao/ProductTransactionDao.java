package com.user.dao;

import com.user.model.ProductTransaction;

public interface ProductTransactionDao {

	String saveBuyingDetails(ProductTransaction productTransaction);

	void deleteProductTransaction(String productId);
}
