package com.library.ui.main;

import com.library.jdbc.DBConnection;
import com.library.tables.User;
import com.library.ui.MainWindow;
import com.library.ui.dialogs.LoginDialog;

public class LibraryUI {
	
	private User user;
	
	public LibraryUI() {
		if (DBConnection.getDBConnection() != null) {
			showLoginDialog();
		}
		
		if (isLoginSuccessful()) {
			new MainWindow(this.user);
		}
	}

	private boolean isLoginSuccessful() {
		return this.user != null;
	}

	private void showLoginDialog() {
		LoginDialog loginDialog = new LoginDialog();
		this.user = loginDialog.getUser();
	}

	public static void main(String[] args) {
		new LibraryUI();
	}

}
