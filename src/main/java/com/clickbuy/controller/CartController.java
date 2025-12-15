package com.clickbuy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clickbuy.Exception.ProductException;
import com.clickbuy.model.Cart;
import com.clickbuy.model.CartItem;
import com.clickbuy.model.Product;
import com.clickbuy.model.User;
import com.clickbuy.request.AddItemRequest;
import com.clickbuy.response.ApiResponse;
import com.clickbuy.service.CartItemService;
import com.clickbuy.service.CartService;
import com.clickbuy.service.ProductService;
import com.clickbuy.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
	
	private final CartItemService cartItemService;
	private final CartService cartService;
	private final UserService userService;
	private final ProductService productService;
	
	
	@GetMapping 
	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserByJwtToken(jwt);
		
		Cart userCart = cartService.findUserCart(user);
		
		return new ResponseEntity<>(userCart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt) throws ProductException, Exception{
		User user = userService.findUserByJwtToken(jwt);
		
		Product product = productService.findProductById(req.getProductId());
		
		CartItem cartItem = cartService.addCartItem(user, product, req.getSize(), req.getQuantity());
		
		ApiResponse response = new ApiResponse();
		response.setMessage("Item added to cart sucessfully");
		
		return new ResponseEntity<CartItem>(cartItem,HttpStatus.ACCEPTED);
	
	}
	
	@DeleteMapping("/item/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItemHandler(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt ) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		
		cartItemService.removecartItem(user.getId(), cartItemId);
		
		ApiResponse res = new ApiResponse();
		res.setMessage("Item remove from cart");
		
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
		
	}
	
	@PutMapping("/item/{cartItemId}")
	public ResponseEntity<CartItem> updateCartItem(@RequestHeader("Authorization") String jwt,@PathVariable Long cartItemId, @RequestBody CartItem cartItem) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		
		CartItem updatedCartItem = null;
		if(cartItem.getQuantity()>0) {
		updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
		}
		
		return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
		
	}
	
	

}