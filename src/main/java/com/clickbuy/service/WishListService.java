package com.clickbuy.service;

import com.clickbuy.model.Product;
import com.clickbuy.model.User;
import com.clickbuy.model.WishList;

public interface WishListService {
	
	WishList createWishList(User user);
	WishList getWishListByUserId(User user);
    WishList addProductToWishList(User user,Product product);

}