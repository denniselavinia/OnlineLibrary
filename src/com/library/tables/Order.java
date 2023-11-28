package com.library.tables;

import java.util.Date;

public class Order {
	
	private int id;
	private String content;
	private int userId;
	private Date orderDate;
	
	public Order(int id, String content, int userId, Date orderDate) {
		this.id = id;
		this.content = content;
		this.userId = userId;
		this.orderDate = orderDate;
	}

	public int getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public int getUserId() {
		return userId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

}
