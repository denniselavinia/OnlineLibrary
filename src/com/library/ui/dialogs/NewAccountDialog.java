package com.library.ui.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.library.tables.dao.UsersDAO;
import com.library.ui.MainWindow;

public class NewAccountDialog {
	
	protected Shell shell = new Shell();
	protected Text username;
	protected Text email;
	protected Text password;
	protected Text passwordConfirm;
	protected Button btnIsAdmin;
	protected Button btnSubmit;
	
	protected boolean modifyUser; // used when modifying the user
	protected MainWindow mainWindow;
	
	public NewAccountDialog() {
		this(false);
		this.mainWindow = null; // called from LoginDialog
	}
	
	public NewAccountDialog(MainWindow mainWindow) {
		this(true);
		this.mainWindow = mainWindow;
	}
	
	public NewAccountDialog(boolean isAdmin) {
		shell.setMinimumSize(350, 150);
		shell.setLayout(new GridLayout(2, false));
		shell.setText("Dialog Cont Nou");
		
		username = addLabelAndText("Username:", "", false);
		email = addLabelAndText("E-mail:", "", false);
		password = addLabelAndText("Password:", "", true);
		passwordConfirm = addLabelAndText("Confirm Password", "", true);

		if (isAdmin) {
			Label lbIsAdmin = new Label(shell, SWT.NULL);
			lbIsAdmin.setText("Administrator:");
			lbIsAdmin.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			btnIsAdmin = new Button(shell, SWT.CHECK);
		}
		
		btnSubmit = new Button(shell, SWT.PUSH);
		btnSubmit.setText("Creeaza cont nou!");
		btnSubmit.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (checkCredentials()) {
					String user = username.getText();
					String e_mail = email.getText();
					String pass = password.getText();
					boolean isAdmin = false;
					if (btnIsAdmin != null) {
						isAdmin = btnIsAdmin.getSelection();
					}
					
					if (modifyUser) {
						TableItem item = mainWindow.getLeftSide().getUsersTable().getSelection()[0];
						int userId = UsersDAO.INSTANCE.getIdFromUserInfo(item.getText(0), item.getText(1), item.getText(2));
						if (UsersDAO.INSTANCE.updateUserWithId(userId, user, pass, e_mail, isAdmin) != -1) {
							showMessageBox("Contul a fost modificat cu success!", SWT.ICON_INFORMATION);
							mainWindow.getLeftSide().populateUsersTable();
							shell.dispose();
						} else {
							showMessageBox("Contul nu a putut fi modificat!", SWT.ICON_WARNING);
						}
					} else {
						if (UsersDAO.INSTANCE.insertNewUser(user, e_mail, pass, isAdmin) == 1) { // insert successful
							showMessageBox("Contul a fost adaugat cu success!", SWT.ICON_INFORMATION);
							if (mainWindow != null) {
								mainWindow.getLeftSide().populateUsersTable(); // refresh Users tab
							}
							shell.dispose();
						} else {
							showMessageBox("Contul nu a putut fi adaugat!", SWT.ICON_WARNING);
						}
					}
				}
			}
			
		});
		
		shell.pack();
		shell.open();
	}
	
	protected Text addLabelAndText(String strLabel, String strText, boolean isHidden) {
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
	
	protected boolean checkCredentials() {
		if (this.username.getText().contains(" ")) {
			showMessageBox("Username-ul contine spatii!", SWT.ICON_WARNING);
			return false;
		} else if (this.email.getText().contains(" ")) {
			showMessageBox("Email-ul contine spatii!", SWT.ICON_WARNING);
			return false;
		} else if (this.username.getText().length() < 8 || this.password.getText().length() < 8) {
			showMessageBox("Lungimea minima a username-ului si parolei e de 8 caractere!", SWT.ICON_WARNING);
			return false;
		} else if (!this.email.getText().contains("@")) {
			showMessageBox("Formatul email-ului este invalid!", SWT.ICON_WARNING);
			return false;
		} else if (!this.password.getText().equals(this.passwordConfirm.getText())) {
			showMessageBox("Parola de confirmare nu corespunde cu parola introdusa!", SWT.ICON_WARNING);
			return false;
		}
		
		return true;
	}

	protected void showMessageBox(String message, int type) {
		MessageBox messageBox = new MessageBox(shell, SWT.OK | type | SWT.CANCEL);
		messageBox.setMessage(message);
		messageBox.open();
	}

}
