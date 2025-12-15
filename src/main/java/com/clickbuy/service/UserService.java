package com.clickbuy.service;

import com.clickbuy.model.User;


public interface UserService {

	User findUserByJwtToken(String jwt) throws Exception;
	
	User findUserByEmail(String email) throws Exception;



	
}
