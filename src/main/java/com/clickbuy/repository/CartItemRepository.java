package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.model.Cart;
import com.clickbuy.model.CartItem;
import com.clickbuy.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
      CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}