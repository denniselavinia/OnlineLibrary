package com.library.tables.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.jdbc.DBConnection;
import com.library.tables.Inventory;

public class InventoryDAO {
	
	public static final InventoryDAO INSTANCE = new InventoryDAO();
	
	public List<Inventory> getInventory(){
		List<Inventory> inventory = new ArrayList<>();
		String query = "SELECT `books`.`name` as name, quantity FROM `inventory` INNER JOIN books on `inventory`.`book_id` = `books`.`id`";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Inventory entry = new Inventory(
						rs.getString("name"),
						rs.getInt("quantity")
						);
				inventory.add(entry);
			}
		} catch (SQLException e) {
			System.out.println("InventoryDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return inventory;
	}
	
	public int updateQuantityForBookWithId(int bookId, int quantity) {
		String query = "UPDATE inventory SET quantity = '" + quantity + "'" + " WHERE book_id = '" + bookId + "'";
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1; //error
	}

}
