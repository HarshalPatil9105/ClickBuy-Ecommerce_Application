package com.clickbuy.controller;

import java.net.Authenticator.RequestorType;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clickbuy.domain.USER_ROLE;
import com.clickbuy.model.User;
import com.clickbuy.model.VerificationCode;
import com.clickbuy.repository.UserRepository;
import com.clickbuy.request.LoginOtpRequest;
//import com.clickbuy.request.LoginOtpRequest;
import com.clickbuy.request.LoginRequest;
import com.clickbuy.response.ApiResponse;
import com.clickbuy.response.AuthResponse;
import com.clickbuy.response.SignUpRequest;
import com.clickbuy.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final UserRepository userRepository;
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignUpRequest req) throws Exception {
		String jwt = authService.createUser(req);

		AuthResponse res = new AuthResponse();
		res.setJwt(jwt);
		res.setMessage("Registration Succesfull");
		res.setRole(USER_ROLE.ROLE_CUSTOMER);

		return ResponseEntity.ok(res);

	}

	@PostMapping("/sent/login-signup-otp")
	public ResponseEntity<ApiResponse> sentOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {
		authService.sentLoginOtp(req.getEmail(),req.getRole());

		ApiResponse res = new ApiResponse();

		res.setMessage("Otp send succesfully");

		return ResponseEntity.ok(res);
  
	}

	@PostMapping("/signing")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {
		AuthResponse authResponse = authService.signing(req);
		return ResponseEntity.ok(authResponse);

	}

}