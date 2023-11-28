package com.library.ui.dialogs;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.library.jdbc.DBConnection;
import com.library.tables.User;

public class LoginDialog {
	
	private Display display = Display.getDefault();
	private Shell shell = new Shell(display);
	private Label lbWelcome;
	private Text username;
	private Text password;
	private Button btnSubmit;
	private Button btnNewAccount;
	private User user;

	public LoginDialog() {
		this("admin", "admin");
//		this("dennise_stoian", "dennise_stoian");
	}
	
	public LoginDialog(String user, String pass) {
		shell.setMinimumSize(300, 150);
		shell.setLayout(new GridLayout(2, false));
		shell.setText("Login Dialog");
//		shell.setImage(ImageHandler.IMAGE_BOOK);
		
		lbWelcome = new Label(shell, SWT.NULL);
		lbWelcome.setText("Bine ai venit la Libraria Online!");
		lbWelcome.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, false, 2, 1));
		
		username = addLabelAndText("Username", user, false);
		password = addLabelAndText("Password:", user, true);
		
		btnSubmit = new Button(shell, SWT.PUSH);
		btnSubmit.setText("Login");
		btnSubmit.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		new Label(shell, SWT.NONE); // separator
		Label separator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
	    separator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		btnNewAccount = new Button(shell, SWT.PUSH);
		btnNewAccount.setText("Creeaza cont nou");
		btnNewAccount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		addSelectionListeners();
		addKeyListeners();
		
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		display.dispose();
	}
	
	private Text addLabelAndText(String strLabel, String strText, boolean isHidden) {
		Label label = new Label(shell, SWT.NULL);
		label.setText(strLabel);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Text text = new Text(shell, SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		text.setText(strText);
		text.setTextLimit(30);
		if (isHidden) text.setEchoChar('*');
		
		return text;
	}
	
	private void checkCredentials() {
		String user = username.getText();
		String pass = password.getText();

		if (user == "") {
			MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_WARNING | SWT.CANCEL);
			messageBox.setMessage("Introduceti username-ul.");
			messageBox.open();
		}
		else if (pass == "") {
			MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_WARNING | SWT.CANCEL);
			messageBox.setMessage("Introduceti parola.");
			messageBox.open();
		} 
		else if (loginUser(user, pass)) {
			shell.dispose();
		} 
		else {
			MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_WARNING | SWT.CANCEL);
			messageBox.setMessage("Username sau parola invalide!");
			messageBox.open();
		}
	}
	
	private void addSelectionListeners() {
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				checkCredentials();
			}
		});
		
		btnNewAccount.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				new NewAccountDialog();
			}
		});
		
	}
	
	private void addKeyListeners() {
		KeyAdapter enterListener = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) { // enter key press
					checkCredentials();
				}
			}
		};
		
		username.addKeyListener(enterListener);
		password.addKeyListener(enterListener);
	}

	private boolean loginUser(String username, String password) {
		ResultSet rs = DBConnection.login(username, password);
		
		try {
			if (rs.next()) {
				user = new User(
						rs.getInt("id"),
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("email"),
						rs.getBoolean("isAdmin")
						);
				return true; // login successful
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public User getUser() {
		return this.user;
	}

}