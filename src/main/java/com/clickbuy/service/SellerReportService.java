package com.clickbuy.service;

import com.clickbuy.model.Seller;
import com.clickbuy.model.SellerReport;

public interface SellerReportService {
	
	SellerReport getSellerReport(Seller seller);
	
	SellerReport updateSellerReport(SellerReport sellerReport);

}