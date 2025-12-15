package com.clickbuy.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.clickbuy.model.Order;
import com.clickbuy.model.Seller;
import com.clickbuy.model.Transaction;
import com.clickbuy.repository.SellerReportRepository;
import com.clickbuy.repository.SellerRepository;
import com.clickbuy.repository.TransactionRepository;
import com.clickbuy.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final SellerRepository sellerRepository;
	private final TransactionRepository transactionRepository;

	@Override
	public Transaction createTransaction(Order order) {
		Seller seller = sellerRepository.findById(order.getSellerId()).get();

		Transaction transaction = new Transaction();
		transaction.setSeller(seller);
		transaction.setCustomer(order.getUser());
		transaction.setOrder(order);

		return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> getTransactionBySellerId(Seller seller) {
		
		return transactionRepository.findBySellerId(seller.getId());
	}

	@Override
	public List<Transaction> getAllTransaction() {
		
		return transactionRepository.findAll();
	}

}