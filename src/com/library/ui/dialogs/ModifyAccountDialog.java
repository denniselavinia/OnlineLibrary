package com.library.ui.dialogs;

import org.eclipse.swt.widgets.Button;

import com.library.ui.MainWindow;

public class ModifyAccountDialog extends NewAccountDialog {
	
	protected Button btnModifyUser;
	
	public ModifyAccountDialog(MainWindow mainWindow, String user, String pass, String em, boolean isAdmin) {
		super(true);
		this.shell.setText("Dialog Modifica Cont");
		this.mainWindow = mainWindow;
		this.username.setText(user);
		this.email.setText(em);
		this.btnIsAdmin.setSelection(isAdmin);
		this.modifyUser = true;
		
		this.btnSubmit.setText("Modifica contul");
	}

}
