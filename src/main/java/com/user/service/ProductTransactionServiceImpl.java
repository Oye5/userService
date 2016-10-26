package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.ProductTransactionDao;
import com.user.model.ProductTransaction;

@Service
@Transactional
public class ProductTransactionServiceImpl implements ProductTransactionService {

	@Autowired
	ProductTransactionDao transactionDao;

	@Override
	public String saveBuyingDetails(ProductTransaction productTransaction) {
		return transactionDao.saveBuyingDetails(productTransaction);

	}

	@Override
	public void deleteProductTransaction(String productId) {
		transactionDao.deleteProductTransaction(productId);

	}

	@Override
	public List<ProductTransaction> getBuyingProductByUserId(String userId) {
		return transactionDao.getBuyingProductByUserId(userId);
	}

	@Override
	public List<ProductTransaction> getSoldProductByUserId(String userId) {
		return transactionDao.getSoldProductByUserId(userId);
	}

}
