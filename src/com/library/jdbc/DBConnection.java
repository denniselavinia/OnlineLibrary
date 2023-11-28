package com.library.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBConnection {
	
	private static Connection connection;
	private static String dbUrl = "jdbc:mysql://localhost:3306/library";
	private static String username = "root";
	private static String password = "root";
	
	public static Connection getDBConnection() {
		if (connection != null) return connection;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // check if jdbc driver is available
			connection = DriverManager.getConnection(dbUrl, username, password);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Conexiunea la baza de date a esuat!");
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public static ResultSet login(String username, String password) {
		String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
		System.out.println(query);
		
		try {
			Statement st = getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
