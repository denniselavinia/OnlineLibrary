package com.library.ui.dialogs;

import com.library.ui.MainWindow;

public class ModifyBookDialog extends NewBookDialog {

	public ModifyBookDialog(MainWindow mainWindow, String name, String author, String genre, int price) {
		super(mainWindow);
		this.modifyBook = true;
		
		this.shell.setText("Dialog Modifica Cartea");
		this.txtBookName.setText(name);
		this.txtBookAuthor.setText(author);
		this.txtBookPrice.setText(String.valueOf(price));
		for (int i = 0; i < this.cmbBookGenre.getItems().length; i++) {
			if (cmbBookGenre.getItem(i).contains(genre)) {
				cmbBookGenre.select(i);
				break;
			}
		}
		
		this.btnSubmit.setText("Modifica cartea!");
		
	}
	
	

}
