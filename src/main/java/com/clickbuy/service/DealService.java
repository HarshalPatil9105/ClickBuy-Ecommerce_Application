package com.clickbuy.service;

import java.util.List;

import com.clickbuy.model.Deal;

public interface DealService {

	List<Deal> getDeals();

	Deal createDeal(Deal deal);

	Deal updateDeal(Deal deal, Long id) throws Exception;

	void deleteDeal(Long id) throws Exception;

}