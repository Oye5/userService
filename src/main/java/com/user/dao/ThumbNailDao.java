package com.user.dao;

import com.user.model.ThumbNail;

public interface ThumbNailDao {

	void saveThumbNail(ThumbNail thumb);

	ThumbNail getThumbByProductId(String productId);

	void deleteThumbNail(String productId);
}
