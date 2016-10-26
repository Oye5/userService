package com.user.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.user.dto.request.ResetPasswordRequest;
import com.user.model.User;

@Repository
public class UserDaoImpl extends AbstractDao<String, User> implements UserDao {

	@Override
	public User getUserById(String userId) {
		return getByKey(userId);
	}

	@Override
	public User getUserByEmailId(String userEmail) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("email", userEmail));
		return (User) criteria.uniqueResult();

	}

	@Override
	public void updateUser(User user) {
		update(user);

	}

	@Override
	public User getUserByEmailIdAndOTP(ResetPasswordRequest request) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("email", request.getEmail()));
		criteria.add(Restrictions.eq("otp", request.getOtp()));
		return (User) criteria.uniqueResult();
	}

	@Override
	public void saveUser(User user) {
		persist(user);
	}

	@Override
	public User getUser(String email) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("email", email));
		return (User) criteria.uniqueResult();
	}

	@Override
	public User getUserByAccessToken(String accessToken) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("fbAuthToken", accessToken));
		return (User) criteria.uniqueResult();
	}

}
