package com.library.tables.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.jdbc.DBConnection;
import com.library.tables.Favorites;

public class FavoritesDAO {
	
	public static final FavoritesDAO INSTANCE = new FavoritesDAO();
	
	public List<Favorites> getFavoritesForUserId(int userId) {
		List<Favorites> userFavorites = new ArrayList<>();
		String query = "SELECT * FROM favorites WHERE user_id = '" + userId + "'";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Favorites favorites = new Favorites(
						rs.getInt("user_id"),
						rs.getString("product_name"),
						rs.getInt("price")
						);
				userFavorites.add(favorites);
			}
		} catch (SQLException e) {
			System.out.println("FavoritesDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return userFavorites;
	}
	
	public List<Favorites> getFavoritesForAllUsers(int userId) {
		List<Favorites> userFavorites = new ArrayList<>();
		String query = "SELECT * FROM favorites WHERE user_id = '" + userId + "'";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Favorites favorites = new Favorites(
						rs.getInt("user_id"),
						rs.getString("product_name"),
						rs.getInt("price")
						);
				userFavorites.add(favorites);
			}
		} catch (SQLException e) {
			System.out.println("FavoritesDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return userFavorites;
	}
	
	public int removeFavoriteProductFromUserWithId(String productName, int userId) {
		String query = "DELETE FROM favorites WHERE product_name = '" + productName + "' and user_id = " + userId;
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int removeAllFavoriteProductsFromUserWithId(int userId) {
		String query = "DELETE FROM favorites WHERE user_id = " + userId;
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public ResultSet checkProductIntoFavoritesForUserId(String productName, int userId) {
		String query = "Select * FROM favorites WHERE product_name = '" + productName + "' and user_id = " + userId;
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
	
	public int insertProductIntoFavoritesForUserId(String productName, int price, int userId) {
		ResultSet rs = checkProductIntoFavoritesForUserId(productName, userId);
		Favorites product = null;
		
		try {
			while (rs.next()) {
				product = new Favorites(
						rs.getInt("user_id"),
						rs.getString("product_name"),
						rs.getInt("price")
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (product == null) { // product doesn't exist in users' basket
			String query = "INSERT INTO favorites (user_id, product_name, price) VALUES ('" + userId + "', '" + productName + "', '" + price + "')";
			System.out.println(query);
			try {
				PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
				return ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return -1; // error
	}

}
