package com.library.tables.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.jdbc.DBConnection;
import com.library.tables.User;

public class UsersDAO {
	
	public static final UsersDAO INSTANCE = new UsersDAO();
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM users";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				User user = new User(
						rs.getInt("id"),
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("email"),
						rs.getBoolean("isAdmin")
						);
				users.add(user);
			}
		} catch (SQLException e) {
			System.out.println("UsersDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return users;
	}
	
	public int insertNewUser(String username, String email, String password, boolean isAdmin) {
		String query = "INSERT INTO users (id, username, password, email, isAdmin) "
				+ "VALUES (NULL, '" + username + "', '" + password + "', '" + email + "', " + (isAdmin ? "'1'" : "'0'") +")";
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1; // error
	}
	
	public int updateUserWithId(int userId, String username, String password, String email, boolean isAdmin) {
		String query = "UPDATE users SET username = '" + username + "', email = '" + email + "', password = '" + password + "', isAdmin = " + (isAdmin ? "'1'" : "'0'") + " WHERE id = '" + userId + "'";
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1; //error
	}
	
	public int removeUserWithInfo(String username, String password, String email) {
		String query = "DELETE FROM users WHERE username = '" + username + "' and email = '" + email + "' and password = '" + password + "'";
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1; // error
	}
	
	public int getIdFromUserInfo(String username, String password, String email) {
		String query = "SELECT id FROM users WHERE username = '" + username + "' and email = '" + email + "' and password = '" + password + "'";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("UsersDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return -1; // error
	}

}
