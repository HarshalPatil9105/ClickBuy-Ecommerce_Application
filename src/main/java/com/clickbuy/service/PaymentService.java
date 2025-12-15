package com.clickbuy.service;

import java.util.Set;

import com.clickbuy.model.Order;
import com.clickbuy.model.PaymentOrder;
import com.clickbuy.model.User;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {

	PaymentOrder createOrder(User user, Set<Order> orders);

	PaymentOrder getPaymentOrderById(Long orderId) throws Exception;

	PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception;

	Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException;
	
	PaymentLink createRazorpayPaymentLink(User user, Long amount,Long orderId) throws RazorpayException;
	
	String createStripePaymentLink(User user, Long amount, Long orderId) throws StripeException;

}