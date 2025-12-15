package com.clickbuy.service.impl;

import org.springframework.stereotype.Service;

import com.clickbuy.model.Product;
import com.clickbuy.model.User;
import com.clickbuy.model.WishList;
import com.clickbuy.repository.WishListRepository;
import com.clickbuy.service.WishListService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishListImpl implements WishListService {

	private final WishListRepository wishListRepository;

	@Override
	public WishList createWishList(User user) {
		WishList wishList = new WishList();
		wishList.setUser(user);
		return wishListRepository.save(wishList);
	}

	@Override
	public WishList getWishListByUserId(User user) {
		WishList wishList = wishListRepository.findByUserId(user.getId());
		if (wishList == null) {
			wishList = createWishList(user);
		}
		return wishList;
	}

	@Override
	public WishList addProductToWishList(User user, Product product) {
		WishList wishList = getWishListByUserId(user);
		if (wishList.getProducts().contains(product)) {
			wishList.getProducts().remove(product);
			
		}else {
			wishList.getProducts().add(product);
		}
		
		return wishListRepository.save(wishList);
	}

}