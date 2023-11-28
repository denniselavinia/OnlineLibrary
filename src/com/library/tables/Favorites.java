package com.library.tables;

public class Favorites {
	
	private int userId;
	private String productName;
	private int price;
	
	public Favorites(int userId, String productName, int price) {
		this.userId = userId;
		this.productName = productName;
		this.price = price;
	}

	public int getUserId() {
		return userId;
	}

	public String getProductName() {
		return productName;
	}

	public int getPrice() {
		return price;
	}
	
}
