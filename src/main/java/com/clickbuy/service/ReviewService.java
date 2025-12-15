package com.clickbuy.service;

import java.util.List;

import com.clickbuy.model.Product;
import com.clickbuy.model.Review;
import com.clickbuy.model.User;
import com.clickbuy.request.CreateReviewRequest;

public interface ReviewService {

	Review createReview(CreateReviewRequest req, User user, Product product);

	List<Review> getReviewByProductId(Long productId);

	Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception;

	void deleteReview(Long reviewId, Long userId) throws Exception;

	Review getReviewById(Long reviewId) throws Exception;

}