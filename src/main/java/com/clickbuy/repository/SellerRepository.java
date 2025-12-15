package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.domain.AccountStatus;
import com.clickbuy.model.Seller;

import java.util.List;



public interface SellerRepository extends JpaRepository<Seller, Long> {
	Seller findByEmail(String email);
	List<Seller> findByAccountStatus(AccountStatus accountStatus);

}