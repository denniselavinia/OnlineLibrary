package com.library.tables.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.jdbc.DBConnection;
import com.library.tables.Book;

public class BooksDAO {
	
	public static final BooksDAO INSTANCE = new BooksDAO();
	
	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		String query = "SELECT `books`.`id`, `books`.`name`, author, `genres`.`name` AS genre, price"
				+ " FROM `books` INNER JOIN genres ON books.genre = genres.id";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Book book = new Book(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("author"),
						rs.getString("genre"),
						rs.getInt("price")
						);
				books.add(book);
			}
		} catch (SQLException e) {
			System.out.println("BooksDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return books;
	}
	
	public int insertNewBook(String name, String author, int genreId, int price) {
		String query = "INSERT INTO books (id, name, author, genre, price) "
				+ "VALUES (NULL, '" + name + "', '" + author + "', '" + genreId + "', " + price +")";
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1; // error
	}
	
	public int removeBookWithInfo(String name, String author, int price) {
		String query = "DELETE FROM books WHERE name = '" + name + "' and author = '" + author + "' and price = '" + price + "'";
		System.out.println(query);
		
		try {
			PreparedStatement ps = DBConnection.getDBConnection().prepareStatement(query);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1; // error
	}
	
	public int getIdFromBookInfo(String name, String author, int price) {
		String query = "SELECT id FROM books WHERE name = '" + name + "' and price = '" + price + "' and author = '" + author + "'";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("BooksDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return -1; // error
	}
	
	public int getIdFromBookName(String name) {
		String query = "SELECT id FROM books WHERE name = '" + name + "'";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println("BooksDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return -1; // error
	}
	
	public int updateBookWithId(int bookId, String name, String author, int genre, int price) {
		String query = "UPDATE books SET name = '" + name + "', genre = '" + genre + "', author = '" + author + "', price = " + price + " WHERE id = '" + bookId + "'";
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
