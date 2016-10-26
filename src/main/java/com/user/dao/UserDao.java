package com.user.dao;

import com.user.dto.request.ResetPasswordRequest;
import com.user.model.User;

public interface UserDao {

	User getUserById(String userId);

	User getUserByEmailId(String userEmail);

	void updateUser(User user);

	User getUserByEmailIdAndOTP(ResetPasswordRequest request);

	void saveUser(User user);

	User getUser(String email);

	User getUserByAccessToken(String accessToken);
}
