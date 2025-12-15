package com.clickbuy.service;

import java.util.List;

import com.clickbuy.model.Order;
import com.clickbuy.model.Seller;
import com.clickbuy.model.Transaction;

public interface TransactionService {
	
	Transaction createTransaction(Order order);
	
	List<Transaction> getTransactionBySellerId(Seller seller);
	
	List<Transaction> getAllTransaction();
	
	

}