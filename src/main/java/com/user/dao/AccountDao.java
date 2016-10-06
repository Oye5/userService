package com.user.dao;

import com.user.model.Accounts;

public interface AccountDao {

	Accounts getAccountByAuthToken(String authToken);

	int updateAccount(Accounts account);

	void createAccount(Accounts account);
}
