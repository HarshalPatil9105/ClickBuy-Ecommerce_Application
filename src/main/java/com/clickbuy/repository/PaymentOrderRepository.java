package com.clickbuy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clickbuy.model.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
	
		PaymentOrder findByPaymentLinkId(String paymentId);

}