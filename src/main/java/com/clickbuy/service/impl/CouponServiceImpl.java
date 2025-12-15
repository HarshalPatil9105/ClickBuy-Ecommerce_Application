package com.clickbuy.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.clickbuy.controller.UserController;
import com.clickbuy.model.Cart;
import com.clickbuy.model.Coupon;
import com.clickbuy.model.User;
import com.clickbuy.repository.CartRepository;
import com.clickbuy.repository.CouponRepository;
import com.clickbuy.repository.UserRepository;
import com.clickbuy.service.CouponService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

	private final UserController userController;

	private final UserRepository userRepository;

	private final CouponRepository coupenRepository;
	private final CartRepository cartRepository;

	@Override
	public Cart applyCoupen(String code, double orderValue, User user) throws Exception {
		Coupon coupen = coupenRepository.findByCode(code);
		Cart cart = cartRepository.findByUserId(user.getId());

		if (coupen == null) {
			throw new Exception("Coupen not valid");
		}

		if (user.getUsedCoupons().contains(coupen)) {
			throw new Exception("Coupen all ready used");
		}

		if (orderValue < coupen.getMinimumOrderValue()) {
			throw new Exception("Valid for minimum order value: " + coupen.getMinimumOrderValue());
		}

		if (coupen.isActive() && LocalDate.now().isAfter(coupen.getValidityStartDate())
				&& LocalDate.now().isBefore(coupen.getValidityEndDate())) {

			user.getUsedCoupons().add(coupen);
			userRepository.save(user);

			double discountPrice = (cart.getTotalSellingPrice() * coupen.getDiscountPercentage()) / 100;

			cart.setTotalSellingPrice(cart.getTotalSellingPrice() - discountPrice);
			cart.setCouponCode(code);
			cartRepository.save(cart);

			return cart;

		}

		throw new Exception("Coupen is not valid");
	}

	@Override
	public Cart removedCoupen(String code, User user) throws Exception {
		Coupon coupen = coupenRepository.findByCode(code);

		if (coupen == null) {
			throw new Exception("Coupen not found");
		}

		Cart cart = cartRepository.findByUserId(user.getId());

		double discountPrice = (cart.getTotalSellingPrice() * coupen.getDiscountPercentage()) / 100;

		cart.setTotalSellingPrice(cart.getTotalSellingPrice() + discountPrice);

		cart.setCouponCode(null);

		return cartRepository.save(cart);
	}

	@Override
	public Coupon findCoupenById(Long id) throws Exception {
		return coupenRepository.findById(id).orElseThrow(() -> new Exception("Coupen not found"));
	}

	@Override
	@PreAuthorize("hasRole ('ADMIN')")
	public Coupon createCoupen(Coupon coupen) {

		return coupenRepository.save(coupen);
	}

	@Override
	public List<Coupon> findAllCoupen() {

		return coupenRepository.findAll();
	}

	@Override
	@PreAuthorize("hasRole ('ADMIN')")
	public void deleteCoupen(Long id) throws Exception {

		findCoupenById(id);
		coupenRepository.deleteById(id);

	}

}