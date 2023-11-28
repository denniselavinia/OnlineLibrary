package com.library.tables.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.jdbc.DBConnection;
import com.library.tables.Basket;

public class BasketDAO {
	
	public static final BasketDAO INSTANCE = new BasketDAO();
	
	public List<Basket> getBasketForUserId(int userId) {
		List<Basket> userBasket = new ArrayList<>();
		String query = "SELECT * FROM basket WHERE user_id = '" + userId + "'";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Basket basket = new Basket(
						rs.getInt("user_id"),
						rs.getString("product_name"),
						rs.getInt("quantity"),
						rs.getInt("price")
						);
				userBasket.add(basket);
			}
		} catch (SQLException e) {
			System.out.println("BasketDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return userBasket;
	}
	
	public int removeBasketForUserId(int userId) {
		String query = "DELETE from basket WHERE user_id = " + userId;
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1; // error
	}
	
	public List<Basket> getBasketForAllUsers() {
		List<Basket> userBasket = new ArrayList<>();
		String query = "SELECT * FROM basket";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Basket basket = new Basket(
						rs.getInt("user_id"),
						rs.getString("product_name"),
						rs.getInt("quantity"),
						rs.getInt("price")
						);
				userBasket.add(basket);
			}
		} catch (SQLException e) {
			System.out.println("BasketDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return userBasket;
	}
	
	public int changeQuantityForProductOfUser(String productName, int newQuantity, int userId) {
		String query;
		if (newQuantity <= 0) {
			query = "DELETE FROM basket WHERE product_name = '" + productName + "' and user_id = " + userId;
		} else {
			query = "UPDATE basket SET quantity = " + newQuantity + " WHERE product_name = '" + productName + "' and user_id = " + userId;
		}
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1; // error
	}
	
	public ResultSet checkProductIntoBasketForUserId(String productName, int userId) {
		String query = "Select * FROM basket WHERE product_name = '" + productName + "' and user_id = " + userId;
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null; // error
	}
	
	public int insertProductIntoBasketForUserId(String productName, int price, int userId) {
		ResultSet rs = checkProductIntoBasketForUserId(productName, userId);
		Basket product = null;
		
		try {
			while (rs.next()) {
				product = new Basket(
						rs.getInt("user_id"),
						rs.getString("product_name"),
						rs.getInt("quantity"),
						rs.getInt("price")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (product == null) { // product doesn't exist in users' basket
			String query = "INSERT INTO basket (user_id, product_name, quantity, price) VALUES ('" + userId + "', '" + productName + "', '" + 1 + "', '" + price + "')";
			System.out.println(query);
			try {
				PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
				return ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			this.changeQuantityForProductOfUser(product.getProductName(), product.getQuantity() + 1, userId);
		}
		
		return -1; // error
	}

}
