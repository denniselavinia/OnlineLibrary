package com.library.ui.leftside;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import com.library.tables.User;
import com.library.tables.dao.BooksDAO;
import com.library.tables.dao.UsersDAO;
import com.library.ui.MainWindow;
import com.library.ui.dialogs.ModifyAccountDialog;
import com.library.ui.dialogs.ModifyBookDialog;
import com.library.ui.dialogs.ModifyInventoryDialog;
import com.library.ui.dialogs.NewAccountDialog;
import com.library.ui.dialogs.NewBookDialog;

public class AdminActions extends Composite {
	
	@SuppressWarnings("unused")
	private User user;
	private MainWindow mainWindow;
	
	private Group grBooksAdmin;
	private Group grInventoryAdmin;
	private Group grUsersAdmin;
	
	private Button btnAddBook;
	private Button btnDelBook;
	private Button btnModifyBook;
	
	private Button btnModifyInventory;
	
	private Button btnAddUser;
	private Button btnDelUser;
	private Button btnModifyUser;

	public AdminActions(MainWindow mainwindow, User user, Composite parent, int style) {
		super(parent, style);
		this.user = user;
		this.mainWindow = mainwindow;
		
		this.setLayout(new GridLayout(1, true));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		createBooksGroup();
		createUsersGroup();
		createInventoryGroup();
		addListeners();
	}
	
	private void createBooksGroup() {
		this.grBooksAdmin = new Group(this, SWT.BORDER_DASH);
		this.grBooksAdmin.setText("Administrare Carti");
		this.grBooksAdmin.setLayout(new GridLayout(1, true));
		this.grBooksAdmin.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.btnAddBook = new Button(grBooksAdmin, SWT.PUSH);
		this.btnAddBook.setText("Adauga o carte noua");
		this.btnAddBook.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		this.btnDelBook = new Button(grBooksAdmin, SWT.PUSH);
		this.btnDelBook.setText("Sterge cartea selectata");
		this.btnDelBook.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		this.btnModifyBook = new Button(grBooksAdmin, SWT.PUSH);
		this.btnModifyBook.setText("Modifica cartea selectata");
		this.btnModifyBook.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
	}
	
	private void createUsersGroup() {
		this.grUsersAdmin = new Group(this, SWT.BORDER_DASH);
		this.grUsersAdmin.setText("Administrare Utilizatori");
		this.grUsersAdmin.setLayout(new GridLayout(1, true));
		this.grUsersAdmin.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.btnAddUser = new Button(grUsersAdmin, SWT.PUSH);
		this.btnAddUser.setText("Adauga un utilizator nou");
		this.btnAddUser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		this.btnDelUser = new Button(grUsersAdmin, SWT.PUSH);
		this.btnDelUser.setText("Sterge utilizatorul selectat");
		this.btnDelUser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		this.btnModifyUser = new Button(grUsersAdmin, SWT.PUSH);
		this.btnModifyUser.setText("Modifica utilizatorul selectat");
		this.btnModifyUser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		
		
	}
	
	private void createInventoryGroup() {
		this.grInventoryAdmin = new Group(this, SWT.BORDER_DASH);
		this.grInventoryAdmin.setText("Administrare Inventar");
		this.grInventoryAdmin.setLayout(new GridLayout(1, true));
		this.grInventoryAdmin.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.btnModifyInventory = new Button(grInventoryAdmin, SWT.PUSH);
		this.btnModifyInventory.setText("Modifica inventarul pentru cartea selectata");
		this.btnModifyInventory.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
	}
	
	private void addListeners() {
		addBookListeners();
		addUsersListeners();
		addInventoryListeners();
	}
	
	private void addBookListeners() {
		this.btnAddBook.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new NewBookDialog(mainWindow);
			}
		});
		
		this.btnDelBook.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (mainWindow.getLeftSide().getBooksTable().getSelection().length > 0) {
					TableItem item = mainWindow.getLeftSide().getBooksTable().getSelection()[0];
					int bookPrice = Integer.parseInt(item.getText(3).replaceAll(" Ron", ""));
					if (BooksDAO.INSTANCE.removeBookWithInfo(item.getText(0), item.getText(1), bookPrice) != -1) {
						MessageBox messageBox = new MessageBox(new Shell(), SWT.OK | SWT.ICON_INFORMATION | SWT.CANCEL);
						messageBox.setMessage("Cartea a fost stearsa cu success!");
						messageBox.open();
						mainWindow.getLeftSide().populateBooksTable();
					} else {
						MessageBox messageBox = new MessageBox(new Shell(), SWT.OK | SWT.ICON_WARNING | SWT.CANCEL);
						messageBox.setMessage("Cartea nu a putut fi stearsa!");
						messageBox.open();
					}
				}
			}
		});
		
		this.btnModifyBook.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (mainWindow.getLeftSide().getBooksTable().getSelection().length > 0) {
					TableItem item = mainWindow.getLeftSide().getBooksTable().getSelection()[0];
					String name = item.getText(0);
					String author = item.getText(1);
					String genre = item.getText(2);
					int price = Integer.parseInt(item.getText(3).replaceAll(" Ron", ""));
					new ModifyBookDialog(mainWindow, name, author, genre, price);
				}
			}
		});
	}
	
	private void addUsersListeners() {
		this.btnAddUser.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new NewAccountDialog(mainWindow);
			}
		});
		
		this.btnDelUser.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (mainWindow.getLeftSide().getUsersTable().getSelection().length > 0) {
					TableItem item = mainWindow.getLeftSide().getUsersTable().getSelection()[0];
					if (UsersDAO.INSTANCE.removeUserWithInfo(item.getText(0), item.getText(1), item.getText(2)) != -1) {
						MessageBox messageBox = new MessageBox(new Shell(), SWT.OK | SWT.ICON_INFORMATION | SWT.CANCEL);
						messageBox.setMessage("Contul a fost sters cu success!");
						messageBox.open();
						mainWindow.getLeftSide().populateUsersTable();
					} else {
						MessageBox messageBox = new MessageBox(new Shell(), SWT.OK | SWT.ICON_WARNING | SWT.CANCEL);
						messageBox.setMessage("Contul nu a putut fi sters!");
						messageBox.open();
					}
				}
			}
		});
		
		this.btnModifyUser.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (mainWindow.getLeftSide().getUsersTable().getSelection().length > 0) {
					TableItem item = mainWindow.getLeftSide().getUsersTable().getSelection()[0];
					String username = item.getText(0);
					String password = item.getText(1);
					String email = item.getText(2);
					boolean isAdmin = item.getText(3).equals("Da") ? true : false;
					new ModifyAccountDialog(mainWindow, username, password, email, isAdmin);
				}
			}
		});
	}
	
	private void addInventoryListeners() {
		this.btnModifyInventory.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (mainWindow.getLeftSide().getInventoryTable().getSelection().length > 0) {
					TableItem item = mainWindow.getLeftSide().getInventoryTable().getSelection()[0];
					String bookName = item.getText(0);
					Integer quantity = Integer.parseInt(item.getText(1));
					new ModifyInventoryDialog(mainWindow, bookName, quantity);
				}
			}
		});
	}

}
