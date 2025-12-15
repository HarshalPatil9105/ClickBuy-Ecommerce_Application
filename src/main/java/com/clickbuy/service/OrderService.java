package com.clickbuy.service;

import java.util.List;
import java.util.Set;

import com.clickbuy.domain.OrderStatus;
import com.clickbuy.model.Address;
import com.clickbuy.model.Cart;
import com.clickbuy.model.Order;
import com.clickbuy.model.OrderItem;
import com.clickbuy.model.User;

public interface OrderService {

	Set<Order> createOrder(User user, Address shippingAddress, Cart cart);

	Order findOrderById(long id) throws Exception;

	List<Order> userOrderHistory(Long userId);

	List<Order> sellersOrder(Long sellerId);

	Order updateOrderStatus(long id, OrderStatus status) throws Exception;

	Order cancelOrder(Long orderId, User user) throws Exception;
	
	OrderItem getOrderItemById(Long id) throws Exception;

}