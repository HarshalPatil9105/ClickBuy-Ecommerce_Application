package com.clickbuy.request;

import lombok.Data;

@Data
public class AddItemRequest {
	private String size;
	private int quantity;
	private Long productId;
}
