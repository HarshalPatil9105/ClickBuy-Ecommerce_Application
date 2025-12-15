package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.model.SellerReport;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
	
	SellerReport findBySellerId(Long sellerId);

}