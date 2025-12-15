package com.clickbuy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clickbuy.model.Cart;
import com.clickbuy.model.Coupon;
import com.clickbuy.model.User;
import com.clickbuy.repository.CouponRepository;
import com.clickbuy.service.CartService;
import com.clickbuy.service.CouponService;
import com.clickbuy.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupens")
public class AdminCouponController {

	private final CouponRepository coupenRepository;

	private final CouponService coupenService;
	private final UserService userService;
	private final CartService cartService;

	

	@PostMapping("/apply")
	public ResponseEntity<Cart> applyCoupen(@RequestParam String apply, @RequestParam String code,
			@RequestParam double orderValue, @RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByEmail(jwt);
		Cart cart;

		if (apply.equals("true")) {
			cart = coupenService.applyCoupen(code, orderValue, user);
		} else {
			cart = coupenService.removedCoupen(code, user);
		}

		return ResponseEntity.ok(cart);

	}

	// ADMIN controller
	@PostMapping("/admin/create")
	public ResponseEntity<Coupon> createCoupen(@RequestBody Coupon coupen) {

		Coupon createdCoupen = coupenService.createCoupen(coupen);
		return ResponseEntity.ok(createdCoupen);

	}

	@DeleteMapping("/admin/delete/{id}")
	public ResponseEntity<?> deleteCoupen(@PathVariable Long id) throws Exception {

		coupenService.deleteCoupen(id);
		return ResponseEntity.ok("Copen deleted successfully");

	}

	@GetMapping("/admin/all")
	public ResponseEntity<List<Coupon>> getAllCoupen() {

		List<Coupon> allCoupen = coupenService.findAllCoupen();
		return ResponseEntity.ok(allCoupen);

	}

}