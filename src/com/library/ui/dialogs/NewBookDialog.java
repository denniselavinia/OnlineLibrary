package com.library.ui.dialogs;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.library.tables.dao.BooksDAO;
import com.library.tables.dao.GenresDAO;
import com.library.ui.MainWindow;

public class NewBookDialog {
	
	protected MainWindow mainwindow;
	
	protected Label lblBookName;
	protected Text txtBookName;
	protected Label lblBookAuthor;
	protected Text txtBookAuthor;
	protected Label lblBookGenre;
	protected Combo cmbBookGenre;
	protected Label lblBookPrice;
	protected Text txtBookPrice;
	
	protected Button btnSubmit;
	protected boolean modifyBook;
	
	protected Display display = Display.getDefault();
	protected Shell shell = new Shell(display);
	
	public NewBookDialog(MainWindow mainWindow) {
		this.mainwindow = mainWindow;
		
		this.shell.setText("Dialog Adauga Carte Noua");
		this.shell.setLayout(new GridLayout(2, true));
		this.shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.createComponents();
		this.addListeners();
		
		shell.pack();
		shell.open();
	}
	
	private void createComponents() {
		this.lblBookName = new Label(shell, SWT.NONE);
		this.lblBookName.setText("Numele cartii:");
		this.lblBookName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.txtBookName = new Text(shell, SWT.BORDER);
		this.txtBookName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.lblBookAuthor = new Label(shell, SWT.NONE);
		this.lblBookAuthor.setText("Autorul cartii:");
		this.lblBookAuthor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.txtBookAuthor = new Text(shell, SWT.BORDER);
		this.txtBookAuthor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.lblBookPrice = new Label(shell, SWT.NONE);
		this.lblBookPrice.setText("Pretul cartii:");
		this.lblBookPrice.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.txtBookPrice = new Text(shell, SWT.BORDER);
		this.txtBookPrice.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.lblBookGenre = new Label(shell, SWT.NONE);
		this.lblBookGenre.setText("Genul cartii:");
		this.lblBookGenre.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		this.cmbBookGenre = new Combo(shell, SWT.READ_ONLY);
		this.cmbBookGenre.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		List<String> genres = GenresDAO.INSTANCE.getAllGenres().stream().map(g -> g.toString()).collect(Collectors.toList());
		this.cmbBookGenre.setItems(genres.stream().toArray(String[]::new));
		this.cmbBookGenre.select(0);
		
		this.btnSubmit = new Button(shell, SWT.PUSH);
		this.btnSubmit.setText("Adauga cartea!");
		this.btnSubmit.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	}
	
	private void addListeners() {
		this.btnSubmit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String bookName = txtBookName.getText();
				String bookAuthor = txtBookAuthor.getText();
				String price = txtBookPrice.getText();
				int genreId = Integer.parseInt(cmbBookGenre.getText().split("\\.")[0]);
				
				if (modifyBook) {
					if (checkBookInfo()) {
						TableItem item = mainwindow.getLeftSide().getBooksTable().getSelection()[0];
						int pr = Integer.parseInt(item.getText(3).replaceAll(" Ron", ""));
						int bookId = BooksDAO.INSTANCE.getIdFromBookInfo(item.getText(0), item.getText(1), pr);
						if (BooksDAO.INSTANCE.updateBookWithId(bookId, bookName, bookAuthor, genreId, Integer.parseInt(price)) != -1) {
							showMessageBox("Cartea a fost editata cu succes!", SWT.ICON_INFORMATION);
							mainwindow.getLeftSide().populateBooksTable();
							shell.dispose();
						} else {
							showMessageBox("Cartea nu a putut fi modificata!", SWT.ICON_WARNING);
						}
					}
				} else {
					if (checkBookInfo()) {
						if (BooksDAO.INSTANCE.insertNewBook(bookName, bookAuthor, genreId, Integer.parseInt(price)) != -1) {
							showMessageBox("Cartea a fost introdusa cu success!", SWT.ICON_INFORMATION);
							mainwindow.getLeftSide().populateBooksTable();
							shell.dispose();
						} else {
							showMessageBox("Cartea nu a putut fi introdusa cu succes!", SWT.ICON_WARNING);
						}
					}
				}
			}
		});
	}
	
	private boolean checkBookInfo() {
		if (this.txtBookName.getText().isBlank() || this.txtBookName.getText().isEmpty()) {
			showMessageBox("Introduceti numele cartii." ,SWT.ICON_WARNING);
			return false;
		}
		
		if (this.txtBookAuthor.getText().isBlank() || this.txtBookAuthor.getText().isEmpty()) {
			showMessageBox("Introduceti autorul cartii." ,SWT.ICON_WARNING);
			return false;
		}
		
		if (this.txtBookPrice.getText().isBlank() || this.txtBookPrice.getText().isEmpty()) {
			showMessageBox("Introduceti pretul cartii." ,SWT.ICON_WARNING);
			return false;
		}
		
		for (char c : this.txtBookPrice.getText().toCharArray()) {
			if (c < '0' || c > '9') 
				showMessageBox("Pretul trebuie sa contina doar cifre!", SWT.ICON_WARNING);
		}
		
		return true;
	}
	
	private void showMessageBox(String message, int style) {
		MessageBox messageBox = new MessageBox(new Shell(), SWT.OK | style | SWT.CANCEL);
		messageBox.setMessage(message);
		messageBox.open();
	}

}
