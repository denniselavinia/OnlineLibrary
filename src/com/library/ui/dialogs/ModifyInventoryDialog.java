package com.library.ui.dialogs;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.library.tables.dao.BooksDAO;
import com.library.tables.dao.InventoryDAO;
import com.library.ui.MainWindow;

public class ModifyInventoryDialog {
	
	private MainWindow mainWindow;
	private String bookName;
	private int quantity;
	
	private Label lbBookName;
	private Text txtBookName;
	private Label lbQuantity;
	private Text txtQuantity;
	
	private Button btnSubmit;
	
	private Display display = Display.getDefault();
	private Shell shell = new Shell(display);
	
	public ModifyInventoryDialog(MainWindow mainWindow, String bookName, int quantity) {
		this.mainWindow = mainWindow;
		this.bookName = bookName;
		this.quantity = quantity;
		
		this.shell.setText("Dialog Modifica Inventar");
		this.shell.setLayout(new GridLayout(1, true));
		this.shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.addBookInfo();
		this.addSubmitButton();
		
		shell.pack();
		shell.open();
	}

	private void addBookInfo() {
		Composite book = new Composite(shell, SWT.NONE);
		book.setLayout(new GridLayout(2, true));
		book.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		this.lbBookName = new Label(book, SWT.NONE);
		this.lbBookName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.lbBookName.setText("Nume carte:");
		
		this.txtBookName = new Text(book, SWT.BORDER);
		this.txtBookName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.txtBookName.setText(this.bookName);
		this.txtBookName.setEditable(false);
		
		this.lbQuantity = new Label(book, SWT.NONE);
		this.lbQuantity.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.lbQuantity.setText("Cantitate:");
		
		this.txtQuantity = new Text(book, SWT.BORDER);
		this.txtQuantity.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.txtQuantity.setText(this.quantity + "");
	}
	
	private void addSubmitButton() {
		Label separator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
	    separator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
	    
		this.btnSubmit = new Button(shell,  SWT.PUSH);
		this.btnSubmit.setLayoutData((new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1)));
		this.btnSubmit.setText("Modifica inventarul!");
		this.btnSubmit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (!checkQuantity()) return;
				
				Integer qty = Integer.parseInt(txtQuantity.getText());
				int bookId = BooksDAO.INSTANCE.getIdFromBookName(bookName);
				
				if (InventoryDAO.INSTANCE.updateQuantityForBookWithId(bookId, qty) != -1) {
					showMessageBox("Inventarul a fost modificat cu succes!", SWT.ICON_INFORMATION);
					
					// refresh the inventory table
					mainWindow.getLeftSide().populateInventoryTable();
					
					shell.dispose();
				} else {
					showMessageBox("A aparut o eroare! Inventarul nu a putut fi modificat!", SWT.ICON_WARNING);
				}
			}
		});
		
	}
	
	private boolean checkQuantity() {
		for (int i = 0; i < this.txtQuantity.getText().length(); i++) {
			char c = this.txtQuantity.getText().charAt(i);
			if (c < '0' || c > '9') {
				showMessageBox("Cantitatea trebuie sa contina doar cifre.", SWT.ICON_WARNING);
				return false;
			}
		}
		
		return true;
	}
	
	private void showMessageBox(String message, int type) {
		MessageBox messageBox = new MessageBox(shell, SWT.OK | type | SWT.CANCEL);
		messageBox.setMessage(message);
		messageBox.open();
	}

}
