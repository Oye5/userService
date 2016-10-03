package com.user.service;

import com.user.model.ThumbNail;


public interface ThumbNailService {
	void saveThumbNail(ThumbNail thumb);

	ThumbNail getThumbByProductId(String productId);

	void deleteThumbNail(String productId);
}