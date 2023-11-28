package com.library.tables.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.jdbc.DBConnection;
import com.library.tables.Genre;

public class GenresDAO {
	
	public static final GenresDAO INSTANCE = new GenresDAO();
	
	public List<Genre> getAllGenres() {
		List<Genre> genres = new ArrayList<>();
		String query = "SELECT * FROM genres";
		System.out.println(query);
		
		try {
			Statement st = DBConnection.getDBConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Genre genre = new Genre(
						rs.getInt("id"),
						rs.getString("name")
						);
				genres.add(genre);
			}
		} catch (SQLException e) {
			System.out.println("GenresDAO: Eroare la query:\n" + query);
			e.printStackTrace();
		}
		
		return genres;
	}

}
