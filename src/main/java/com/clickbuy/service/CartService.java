package com.clickbuy.service;

import com.clickbuy.model.Cart;
import com.clickbuy.model.CartItem;
import com.clickbuy.model.Product;
import com.clickbuy.model.User;

public interface CartService {

	public CartItem addCartItem(User user, Product product, String size, int quantity);
	public Cart findUserCart(User user);

}