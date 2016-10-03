package com.user.service;

import com.user.model.ProductTransaction;



public interface ProductTransactionService {

	String saveBuyingDetails(ProductTransaction productTransaction);

	void deleteProductTransaction(String productId);

}
