package com.library.ui.rightside;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.library.tables.Book;
import com.library.tables.Genre;
import com.library.tables.Inventory;
import com.library.tables.Order;
import com.library.tables.User;
import com.library.tables.dao.BasketDAO;
import com.library.tables.dao.BooksDAO;
import com.library.tables.dao.FavoritesDAO;
import com.library.tables.dao.GenresDAO;
import com.library.tables.dao.InventoryDAO;
import com.library.tables.dao.OrdersDAO;
import com.library.tables.dao.UsersDAO;
import com.library.ui.MainWindow;
import com.library.ui.leftside.UserActions;

public class UserTabs extends Composite {
	
	private User user;
	private MainWindow mainWindow;
	private Button addProductsToBasket;
	private Button addProductsToFavorites;
	private TabFolder folder;
	
	// tables
	private Table booksTable;
	private Table genresTable;
	private Table inventoryTable;
	private Table usersTable;
	private Table ordersHistoryTable;
	
	public UserTabs(MainWindow mainWindow, User user, Composite parent, int style) {
		super(parent, style);
		this.user = user;
		this.mainWindow = mainWindow;
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.createButtons();
		this.createTabs();
		this.addListeners();
	}
	
	private void createTabs() {
		this.folder = new TabFolder(this, SWT.NONE);
		this.folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		addBooksTab();
		addGenresTab();
		addInventoryTab();
		addUsersTab();
		addOrdersHistoryTab();
	}
	
	private void createButtons() {
		if (user.isAdmin()) return;
		
		this.addProductsToBasket = new Button(this, SWT.PUSH);
		this.addProductsToBasket.setText("Adauga produsele selectate in cos");
		this.addProductsToBasket.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		this.addProductsToFavorites = new Button(this, SWT.PUSH);
		this.addProductsToFavorites.setText("Adauga produsele selectate la favorite");
		this.addProductsToFavorites.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	}
	
	private void addListeners() {
		if (user.isAdmin()) return;
		
		this.addProductsToBasket.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				for (int i = 0; i < booksTable.getSelection().length; i++) {
					TableItem item = booksTable.getSelection()[i];
					String productName = item.getText(0);
					int price = Integer.parseInt(item.getText(3).replaceAll(" Ron", ""));
					
					BasketDAO.INSTANCE.insertProductIntoBasketForUserId(productName, price, user.getId());
				}
				
				if (mainWindow.getRightSide() instanceof UserActions)
					((UserActions)mainWindow.getRightSide()).populateBasketTable(); // refresh basket table
			}
		});
		
		this.addProductsToFavorites.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				for (int i = 0; i < booksTable.getSelection().length; i++) {
					TableItem item = booksTable.getSelection()[i];
					String productName = item.getText(0);
					int price = Integer.parseInt(item.getText(3).replaceAll(" Ron", ""));
					
					FavoritesDAO.INSTANCE.insertProductIntoFavoritesForUserId(productName, price, user.getId()); 
				}
				
				if (mainWindow.getRightSide() instanceof UserActions)
					((UserActions)mainWindow.getRightSide()).populateFavoritesTable(); // refresh favorites table
			}
		});
	}
	
	private void addBooksTab() {
		TabItem booksTab = new TabItem(folder, SWT.NONE);
		booksTab.setText("Books");
		
		booksTable = new Table(folder, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		booksTable.setLayout(new FillLayout());
		booksTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		booksTable.setHeaderVisible(true);
		booksTable.setBounds(25, 25, 600, 200);
		
		String[] titles = { "Book Name", "Author", "Genre", "Price" };
		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			TableColumn column = new TableColumn(booksTable, SWT.NULL);
			column.setText(titles[loopIndex]);
		}
		
		populateBooksTable();
		booksTab.setControl(booksTable);
	}

	public void populateBooksTable() {
		booksTable.removeAll();
		List<Book> books = BooksDAO.INSTANCE.getAllBooks();
		for (Book book : books) {
			TableItem item = new TableItem(booksTable, SWT.NULL);
			item.setText(0, book.getName());
			item.setText(1, book.getAuthor());
			item.setText(2, book.getGenre());
			item.setText(3, book.getPrice() + " Ron");
		}

		for (int loopIndex = 0; loopIndex < 4; loopIndex++) {
			booksTable.getColumn(loopIndex).pack();
		}
	}
	
	private void addGenresTab() {
		if (!user.isAdmin()) return;
		
		TabItem genresTab = new TabItem(folder, SWT.NONE);
		genresTab.setText("Genres");
		
		genresTable = new Table(folder, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		genresTable.setLayout(new FillLayout());
		genresTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		genresTable.setHeaderVisible(true);
		genresTable.setBounds(25, 25, 600, 200);
		
		String[] genreTitles = { "ID", "Name"};
		for (int loopIndex = 0; loopIndex < genreTitles.length; loopIndex++) {
			TableColumn column = new TableColumn(genresTable, SWT.NULL);
			column.setText(genreTitles[loopIndex]);
		}
		
		populateGenresTable();

		genresTab.setControl(genresTable);
	}

	public void populateGenresTable() {
		genresTable.removeAll();
		List<Genre> genres = GenresDAO.INSTANCE.getAllGenres();
		for (Genre genre : genres) {
			TableItem item = new TableItem(genresTable, SWT.NULL);
			item.setText(0, genre.getId() + "");
			item.setText(1, genre.getName());
		}

		for (int loopIndex = 0; loopIndex < 2; loopIndex++) {
			genresTable.getColumn(loopIndex).pack();
		}
	}
	
	private void addInventoryTab() {
		if (!user.isAdmin()) return;
		
		TabItem inventoryTab = new TabItem(folder, SWT.NONE);
		inventoryTab.setText("Inventory");
		
		inventoryTable = new Table(folder, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		inventoryTable.setLayout(new FillLayout());
		inventoryTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		inventoryTable.setHeaderVisible(true);
		inventoryTable.setBounds(25, 25, 600, 200);
		
		String[] inventoryTitles = { "Name", "Quantity"};
		for (int loopIndex = 0; loopIndex < inventoryTitles.length; loopIndex++) {
			TableColumn column = new TableColumn(inventoryTable, SWT.NULL);
			column.setText(inventoryTitles[loopIndex]);
		}
		
		populateInventoryTable();
		inventoryTab.setControl(inventoryTable);
	}

	public void populateInventoryTable() {
		inventoryTable.removeAll();
		List<Inventory> inventory = InventoryDAO.INSTANCE.getInventory();
		for (Inventory entry : inventory) {
			TableItem item = new TableItem(inventoryTable, SWT.NULL);
			item.setText(0, entry.getBookName());
			item.setText(1, entry.getQuantity() + "");
		}

		for (int loopIndex = 0; loopIndex < 2; loopIndex++) {
			inventoryTable.getColumn(loopIndex).pack();
		}
	}
	
	private void addUsersTab() {
		if (!user.isAdmin()) return;
		
		TabItem usersTab = new TabItem(folder, SWT.NONE);
		usersTab.setText("Users");
		
		usersTable = new Table(folder, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		usersTable.setLayout(new FillLayout());
		usersTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		usersTable.setHeaderVisible(true);
		usersTable.setBounds(25, 25, 600, 200);
		
		String[] usersTitles = { "Username", "Password", "E-mail", "Administrator"};
		for (int loopIndex = 0; loopIndex < usersTitles.length; loopIndex++) {
			TableColumn column = new TableColumn(usersTable, SWT.NULL);
			column.setText(usersTitles[loopIndex]);
		}
		
		populateUsersTable();
		usersTab.setControl(usersTable);
	}

	public void populateUsersTable() {
		usersTable.removeAll();
		List<User> users = UsersDAO.INSTANCE.getAllUsers();
		for (User user : users) {
			TableItem item = new TableItem(usersTable, SWT.NULL);
			item.setText(0, user.getUsername());
			item.setText(1, user.getPassword());
			item.setText(2, user.getEmail());
			item.setText(3, (user.isAdmin() ? "Da" : "Nu"));
		}

		for (int loopIndex = 0; loopIndex < 4; loopIndex++) {
			usersTable.getColumn(loopIndex).pack();
		}
	}
	
	private void addOrdersHistoryTab() {
		if (user.isAdmin()) return;
		
		TabItem ordersTab = new TabItem(folder, SWT.NONE);
		ordersTab.setText("Orders History");
		
		this.ordersHistoryTable = new Table(folder, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		this.ordersHistoryTable.setLayout(new FillLayout());
		this.ordersHistoryTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.ordersHistoryTable.setHeaderVisible(true);
		this.ordersHistoryTable.setBounds(25, 25, 600, 200);
		
		String[] ordersTitles = { "# Comanda", "Content", "Data comenzii"};
		for (int loopIndex = 0; loopIndex < ordersTitles.length; loopIndex++) {
			TableColumn column = new TableColumn(ordersHistoryTable, SWT.NULL);
			column.setText(ordersTitles[loopIndex]);
		}
		
		populateOrderHistoryTable();
		ordersTab.setControl(ordersHistoryTable);
	}

	public void populateOrderHistoryTable() {
		this.ordersHistoryTable.removeAll();
		
		List<Order> orders = OrdersDAO.INSTANCE.getOrdersHistoryForUserId(user.getId());
		for (Order order : orders) {
			TableItem item = new TableItem(ordersHistoryTable, SWT.NULL);
			item.setText(0, order.getId() + "");
			if (order.getContent().length() > 50) {
				item.setText(1, order.getContent().substring(0, 50) + "...");
			} else {
				item.setText(1, order.getContent());
			}
			
			item.setText(2, order.getOrderDate().toString());
		}

		for (int loopIndex = 0; loopIndex < 3; loopIndex++) {
			ordersHistoryTable.getColumn(loopIndex).pack();
		}

	}

	public Table getBooksTable() {
		return booksTable;
	}

	public Table getGenresTable() {
		return genresTable;
	}

	public Table getInventoryTable() {
		return inventoryTable;
	}

	public Table getUsersTable() {
		return usersTable;
	}

	public Table getOrdersHistoryTable() {
		return ordersHistoryTable;
	}
	
}
