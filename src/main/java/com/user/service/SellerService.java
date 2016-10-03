package com.user.service;

import com.user.model.Seller;

public interface SellerService {

	Seller getSellerById(String sellerId);

	void updateSeller(Seller seller);

	void saveSeller(Seller seller);
}
