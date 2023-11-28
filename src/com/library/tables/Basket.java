package com.library.tables;

public class Basket {
	
	private int userId;
	private String productName;
	private int quantity;
	private int price;
	
	public Basket(int userId, String productName, int quantity, int price) {
		this.userId = userId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}

	public int getUserId() {
		return userId;
	}

	public String getProductName() {
		return productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getPrice() {
		return price;
	}
	
}
