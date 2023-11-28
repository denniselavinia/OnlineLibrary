package com.library.tables;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private boolean isAdmin;
	
	public User(int id, String username, String password,
			String email, boolean isAdmin) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.isAdmin = isAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String toString() {
		return getUsername() + " " + 
				getPassword() + " " +
				getEmail() + " " +
				isAdmin();
	}
	
}
