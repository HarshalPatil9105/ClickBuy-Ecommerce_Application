package com.clickbuy.service;

import java.util.List;

import com.clickbuy.model.Home;
import com.clickbuy.model.HomeCategory;

public interface HomeService {
	
	public Home createHomePageData(List<HomeCategory> allCategories);

}