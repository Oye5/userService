package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.UserDao;
import com.user.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	@Override
	public User getUserById(String userId) {
		return userDao.getUserById(userId);
	}

}
