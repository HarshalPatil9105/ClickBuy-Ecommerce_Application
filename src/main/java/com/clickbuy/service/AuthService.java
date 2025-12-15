package com.clickbuy.service;

import org.springframework.stereotype.Service;

import com.clickbuy.domain.USER_ROLE;
import com.clickbuy.request.LoginRequest;
import com.clickbuy.response.AuthResponse;
import com.clickbuy.response.SignUpRequest;

@Service
public interface AuthService {
	
	void sentLoginOtp(String email, USER_ROLE role) throws Exception;
	String createUser(SignUpRequest req) throws Exception;
	AuthResponse signing(LoginRequest req) throws Exception;
	

}