package com.clickbuy.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clickbuy.domain.PaymentMethod;
import com.clickbuy.model.Address;
import com.clickbuy.model.Cart;
import com.clickbuy.model.Order;
import com.clickbuy.model.OrderItem;
import com.clickbuy.model.PaymentOrder;
import com.clickbuy.model.Seller;
import com.clickbuy.model.SellerReport;
import com.clickbuy.model.User;
import com.clickbuy.repository.PaymentOrderRepository;
import com.clickbuy.response.PaymentLinkResponse;
import com.clickbuy.service.CartService;
import com.clickbuy.service.OrderService;
import com.clickbuy.service.PaymentService;
import com.clickbuy.service.SellerReportService;
import com.clickbuy.service.SellerService;
import com.clickbuy.service.UserService;
import com.razorpay.PaymentLink;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

	private final SecurityFilterChain securityFilterChain;

	private final OrderService orderService;
	private final UserService userService;
	private final CartService cartService;
	private final SellerService sellerService;
	private final SellerReportService sellerReportService;
	private final PaymentService paymentService;
	private final PaymentOrderRepository paymentOrderRepository;

	@PostMapping()
	public ResponseEntity<PaymentLinkResponse> createOrderHandler(@RequestBody Address shippingAddress,
			@RequestParam PaymentMethod paymentMethod, @RequestHeader("Authorization") String jwt) throws Exception {

		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartService.findUserCart(user);

		Set<Order> orders = orderService.createOrder(user, shippingAddress, cart);

		PaymentOrder paymentOrder = paymentService.createOrder(user, orders);

		PaymentLinkResponse res = new PaymentLinkResponse();

		if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {

			PaymentLink payment = paymentService.createRazorpayPaymentLink(user, paymentOrder.getAmount(),
					paymentOrder.getId());

			String paymentUrl = payment.get("short_url");
			String paymentUrlId = payment.get("id");

			res.setPayment_link_id(paymentUrl);

			paymentOrder.setPaymentLinkId(paymentUrlId);
			paymentOrderRepository.save(paymentOrder);

		}else {
			String paymentUrl = paymentService.createStripePaymentLink(user, paymentOrder.getAmount(), paymentOrder.getId());
			
			res.setPayment_link_url(paymentUrl);
		}
//
	return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Order orders = orderService.findOrderById(orderId);

		return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);

	}

	@GetMapping("/item/{orderItemId}")
	public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long orderItemId,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		OrderItem orderItem = orderService.getOrderItemById(orderItemId);

		return new ResponseEntity<>(orderItem, HttpStatus.ACCEPTED);

	}

	public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		Order order = orderService.cancelOrder(orderId, user);

		Seller seller = sellerService.getSellerById(order.getSellerId());
		SellerReport report = sellerReportService.getSellerReport(seller);

		report.setCanceledOrders(report.getCanceledOrders() + 1);
		report.setTotalRefunds(report.getTotalRefunds() + order.getTotalSellingPrice());
		sellerReportService.updateSellerReport(report);
		
		return ResponseEntity.ok(order);
	}

}