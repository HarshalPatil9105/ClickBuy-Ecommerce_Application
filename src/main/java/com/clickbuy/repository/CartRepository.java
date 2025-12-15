package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	Cart findByUserId(Long id);

}