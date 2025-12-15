package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clickbuy.model.WishList;

public interface WishListRepository extends JpaRepository<WishList, Long> {
	
	WishList findByUserId(Long userId);

}