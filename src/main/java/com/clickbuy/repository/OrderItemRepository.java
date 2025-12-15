package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}