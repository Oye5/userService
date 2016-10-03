package com.user.service;

import com.user.model.Accounts;



public interface AccountService {

	Accounts getAccountByAuthToken(String authToken);

}
