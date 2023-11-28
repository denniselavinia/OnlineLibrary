package com.library.tables.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.jdbc.DBConnection;
import com.library.tables.Order;

public class OrdersDAO {
	
	public static final OrdersDAO INSTANCE = new OrdersDAO();
	
	public int insertOrderForUser(String content, int userId) {
		String query = "INSERT INTO orders (content, user_id, date) VALUES (?, ?, ?)";
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			ps.setString(1, content);
			ps.setInt(2, userId);
			ps.setDate(3, new Date(System.currentTimeMillis()));
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1; // error
	}
	
	public List<Order> getOrdersHistoryForUserId(int userId) {
		List<Order> userOrders = new ArrayList<>();
		String query = "SELECT * FROM orders WHERE user_id = '" + userId + "'";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Order order = new Order(
						rs.getInt("id"),
						rs.getString("content"),
						rs.getInt("user_id"),
						rs.getDate("date")
						);
				userOrders.add(order);
			}
		} catch (SQLException e) {
			System.out.println("OrderDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return userOrders;
	}

}
