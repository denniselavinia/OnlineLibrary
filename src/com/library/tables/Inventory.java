package com.library.tables;

public class Inventory {
	
	private String bookName;
	private int quantity;
	
	public Inventory() {
		this("", 0);
	}
	
	public Inventory(String bookName, int quantity) {
		this.bookName = bookName;
		this.quantity = quantity;
	}
	
	public String getBookName() {
		return bookName;
	}

	public int getQuantity() {
		return quantity;
	}

	public String toString() {
		return this.bookName + ", " + this.quantity;
	}

}
