package com.clickbuy.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clickbuy.model.Product;
import com.clickbuy.model.Review;
import com.clickbuy.model.User;
import com.clickbuy.request.CreateReviewRequest;
import com.clickbuy.response.ApiResponse;
import com.clickbuy.service.ProductService;
import com.clickbuy.service.ReviewService;
import com.clickbuy.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

	private final ReviewService reviewService;
	private final UserService userService;
	private final ProductService productService;

	@GetMapping("/products/{productId}/reviews")
	public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) {

		List<Review> reviews = reviewService.getReviewByProductId(productId);
		return ResponseEntity.ok(reviews);

	}

	@PostMapping("/products/{productId}/reviews")
	public ResponseEntity<Review> writeReview(@RequestBody CreateReviewRequest req, @PathVariable Long productId,
			@RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByJwtToken(jwt);
		Product product = productService.findProductById(productId);
		Review review = reviewService.createReview(req, user, product);

		return ResponseEntity.ok(review);

	}

	@PatchMapping("/reviews/{reviewId}")
	public ResponseEntity<Review> updateReview(@RequestBody CreateReviewRequest req, @PathVariable Long reviewId,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByEmail(jwt);
		Review review = reviewService.updateReview(reviewId, req.getReviewText(), req.getReviewRating(), user.getId());
		return ResponseEntity.ok(review);
	}
	
	public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		reviewService.deleteReview(reviewId, user.getId());
		
		ApiResponse response = new ApiResponse();
		
		response.setMessage("Review delete successfully");
		
		return ResponseEntity.ok(response);
		
	}

}