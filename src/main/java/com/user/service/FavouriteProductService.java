package com.user.service;

import java.util.List;

import com.user.model.FavouriteProducts;


public interface FavouriteProductService {
	void saveFavouriteProduct(FavouriteProducts favouriteProducts);

	List<FavouriteProducts> getFavouriteProducts(FavouriteProducts favouriteProducts);

	List<FavouriteProducts> getFavouriteProductsByUserId(FavouriteProducts favouriteProducts);
	
	void deleteFavouriteProduct(String productId);
	
	int deleteFavouriteProductByUserId(String userId, String productId);
}
