package com.clickbuy.service.impl;

import org.springframework.stereotype.Service;

import com.clickbuy.model.Seller;
import com.clickbuy.model.SellerReport;
import com.clickbuy.repository.SellerReportRepository;
import com.clickbuy.service.SellerReportService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {
	// For this class controller is in seller controller and order controller
	private final SellerReportRepository sellerReportRepository;  

	@Override
	public SellerReport getSellerReport(Seller seller) {
		SellerReport sr = sellerReportRepository.findBySellerId(seller.getId());
		
		if (sr==null) {
			SellerReport newReport = new SellerReport();
			newReport.setSeller(seller);
			return sellerReportRepository.save(newReport);
		}
		return sr;
	}

	@Override
	public SellerReport updateSellerReport(SellerReport sellerReport) {
		return sellerReportRepository.save(sellerReport);
	}

}