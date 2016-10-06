package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.UserDao;
import com.user.dto.request.ResetPasswordRequest;
import com.user.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public User getUserById(String userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public User getUserByEmailId(String userEmail) {

		return userDao.getUserByEmailId(userEmail);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);

	}

	@Override
	public User getUserByEmailIdAndOTP(ResetPasswordRequest request) {
		return userDao.getUserByEmailIdAndOTP(request);
	}

	@Override
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	public User getUser(String email) {
		return userDao.getUser(email);
	}

}
