package com.clickbuy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findByUserId(Long id);
	List<Order> findBySellerId(Long sellerId);

}