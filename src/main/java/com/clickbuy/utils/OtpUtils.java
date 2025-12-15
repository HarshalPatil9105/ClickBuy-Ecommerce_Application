package com.clickbuy.utils;

import java.util.Random;

import lombok.Data;

@Data
public class OtpUtils {

	public static String generateOtp() {
		int otpLength = 6;
		
		Random random = new Random();
		
		StringBuilder otpBuilder = new StringBuilder();
		
		for(int i=0; i<otpLength; i++) {
			otpBuilder.append(random.nextInt(10));
		}
		return otpBuilder.toString();
	}

	
}
