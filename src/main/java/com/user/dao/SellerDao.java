package com.user.dao;

import com.user.model.Seller;

public interface SellerDao {

	Seller getSellerById(String sellerId);

	void updateSeller(Seller seller);

	void saveSeller(Seller seller);

}
