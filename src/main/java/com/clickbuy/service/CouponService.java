package com.clickbuy.service;

import java.util.List;

import com.clickbuy.model.Cart;
import com.clickbuy.model.Coupon;
import com.clickbuy.model.User;

public interface CouponService {
	
	Cart applyCoupen(String code, double orderValue, User user) throws Exception;
	
	Cart removedCoupen(String code, User user) throws Exception;
	
	Coupon findCoupenById(Long id) throws Exception;
	
	Coupon createCoupen(Coupon coupen);
	
	List<Coupon> findAllCoupen();
	
	void deleteCoupen(Long id) throws Exception;

}