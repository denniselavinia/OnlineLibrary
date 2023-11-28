package com.library.ui.leftside;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.library.tables.Basket;
import com.library.tables.Favorites;
import com.library.tables.User;
import com.library.tables.dao.BasketDAO;
import com.library.tables.dao.FavoritesDAO;
import com.library.ui.MainWindow;
import com.library.ui.dialogs.PlaceOrderDialog;
import com.library.utils.ImageHandler;

public class UserActions extends Composite {
	
	private User user;
	private MainWindow mainWindow;

	private Button btnBuyProductsFromBasket;
	private Button btnDeleteProductsFromBasket;
	private Button btnIncreaseQuantity;
	private Button btnDecreaseQuantity;
	private Label lblTotal;
	
	private Button removeProductFromFavorites;
	private Button removeAllProductsFromFavorites;
	
	private Group grBasket;
	private Group grFavorites;
	private Table tableBasket;
	private Table tableFavorites;

	private Image imgAdd = ImageHandler.IMAGE_ADD;
	private Image imgDel = ImageHandler.IMAGE_DEL;
	
	public UserActions(MainWindow mainWindow, User user, Composite parent, int style) {
		super(parent, style);
		this.user = user;
		this.mainWindow = mainWindow;
		
		this.setLayout(new GridLayout(2, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		createBasketGroup();
		createFavouritesGroup();
		addListeners();
	}
	
	private void createBasketGroup() {
		this.grBasket = new Group(this, SWT.BORDER_DASH);
		this.grBasket.setText("Produse adaugate in cos");
		this.grBasket.setLayout(new GridLayout(2, true));
		this.grBasket.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		this.tableBasket = new Table(grBasket, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		this.tableBasket.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		this.tableBasket.setHeaderVisible(true);
		this.tableBasket.setBounds(25, 25, 600, 500);
		
		String[] columnsHeader = { "Book Name", "Quantity", "Price",};
		
		for (int loopIndex = 0; loopIndex < columnsHeader.length; loopIndex++) {
			TableColumn column = new TableColumn(tableBasket, SWT.NULL);
			column.setText(columnsHeader[loopIndex]);
		}
		this.populateBasketTable();
		
		Composite incrDecrComposite = new Composite(grBasket, SWT.NONE);
		incrDecrComposite.setLayout(new GridLayout(3, false));
		incrDecrComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		this.btnIncreaseQuantity = new Button(incrDecrComposite, SWT.PUSH);
		this.btnIncreaseQuantity.setToolTipText("Creste cantitatea produsului selectat");
		this.btnIncreaseQuantity.setImage(imgAdd);
		
		this.btnDecreaseQuantity = new Button(incrDecrComposite, SWT.PUSH);
		this.btnDecreaseQuantity.setToolTipText("Scade cantitatea produsului selectat");
		this.btnDecreaseQuantity.setImage(imgDel);
		
		this.lblTotal = new Label(incrDecrComposite, SWT.NONE);
		this.lblTotal.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		this.printTotalAmount();
		
		Composite buyDelComposite = new Composite(grBasket, SWT.NONE);
		buyDelComposite.setLayout(new GridLayout(2, false));
		buyDelComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		this.btnBuyProductsFromBasket = new Button(buyDelComposite, SWT.PUSH);
		this.btnBuyProductsFromBasket.setText("Comanda produsele din cos");
		this.btnBuyProductsFromBasket.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		this.btnDeleteProductsFromBasket = new Button(buyDelComposite, SWT.PUSH);
		this.btnDeleteProductsFromBasket.setText("Goleste cosul");
		this.btnDeleteProductsFromBasket.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	}

	private void printTotalAmount() {
		this.lblTotal.setText("Total de plata: " + getAllProductsSum() + " Ron");
	}

	public void populateBasketTable() {
		tableBasket.removeAll();
		
		List<Basket> userBasket = BasketDAO.INSTANCE.getBasketForUserId(user.getId());
		for(int i = 0; i < userBasket.size(); i++) {
			Basket basketItem = userBasket.get(i);
			TableItem item = new TableItem(tableBasket, SWT.NULL);
			item.setText(0, basketItem.getProductName());
			item.setText(1, basketItem.getQuantity() + "");
			item.setText(2, basketItem.getPrice() + " Ron");
		}
		
		for (int loopIndex = 0; loopIndex < 3; loopIndex++) {
			tableBasket.getColumn(loopIndex).pack();
		}
		
		if (lblTotal != null)
			this.printTotalAmount();
	}

	private void createFavouritesGroup() {
		this.grFavorites = new Group(this, SWT.BORDER_DASH);
		this.grFavorites.setText("Produsele tale favorite");
		this.grFavorites.setLayout(new GridLayout(2, true));
		this.grFavorites.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		this.tableFavorites = new Table(grFavorites, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		this.tableFavorites.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		this.tableFavorites.setHeaderVisible(true);
		this.tableFavorites.setBounds(25, 25, 600, 500);
		
		String[] columnsHeader = { "Book Name", "Price"};
		for (int loopIndex = 0; loopIndex < columnsHeader.length; loopIndex++) {
			TableColumn column = new TableColumn(tableFavorites, SWT.NULL);
			column.setText(columnsHeader[loopIndex]);
		}
		this.populateFavoritesTable();
		
		this.removeProductFromFavorites = new Button(grFavorites, SWT.PUSH);
		this.removeProductFromFavorites.setImage(imgDel);
		this.removeProductFromFavorites.setToolTipText("Sterge produsul selectat de la favorite");
		
		this.removeAllProductsFromFavorites = new Button(grFavorites, SWT.PUSH);
		this.removeAllProductsFromFavorites.setText("Goleste lista de produse favorite");
		this.removeAllProductsFromFavorites.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	}

	public void populateFavoritesTable() {
		tableFavorites.removeAll();
		
		List<Favorites> userFavorites = FavoritesDAO.INSTANCE.getFavoritesForUserId(user.getId());
		for(Favorites favoriteItem : userFavorites) {
			TableItem item = new TableItem(tableFavorites, SWT.NULL);
			item.setText(0, favoriteItem.getProductName());
			item.setText(1, favoriteItem.getPrice() + " Ron");
		}
		
		for (int loopIndex = 0; loopIndex < 2; loopIndex++) {
			tableFavorites.getColumn(loopIndex).pack();
		}
	}
	
	private void addListeners() {
		// Basket Group
		this.btnIncreaseQuantity.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tableBasket.getSelection().length > 0) {
					TableItem selectedItem = tableBasket.getSelection()[0];
					String productName = selectedItem.getText(0);
					int newQuantity = Integer.parseInt(selectedItem.getText(1)) + 1;
					selectedItem.setText(1, newQuantity + "");
					printTotalAmount();
					
					// update the DB
					BasketDAO.INSTANCE.changeQuantityForProductOfUser(productName, newQuantity, user.getId());
				}
			}
		});
		
		this.btnDecreaseQuantity.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tableBasket.getSelection().length > 0) {
					TableItem selectedItem = tableBasket.getSelection()[0];
					String productName = selectedItem.getText(0);
					int newQuantity = Integer.parseInt(selectedItem.getText(1)) - 1;
					if (newQuantity == 0) {
						tableBasket.remove(tableBasket.getSelectionIndex());
					} else {
						selectedItem.setText(1, newQuantity + "");
					}
					lblTotal.setText("Total de plata: " + getAllProductsSum() + " Ron");
					
					// update the DB
					BasketDAO.INSTANCE.changeQuantityForProductOfUser(productName, newQuantity, user.getId());
				}
			}
		});
		
		this.btnBuyProductsFromBasket.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new PlaceOrderDialog(user, tableBasket.getItems(), mainWindow);
			}
		});
		
		this.btnDeleteProductsFromBasket.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tableBasket.getItems().length > 0) {
					tableBasket.removeAll();
					
					// update the DB
					BasketDAO.INSTANCE.removeBasketForUserId(user.getId());
					lblTotal.setText("Total de plata: " + getAllProductsSum() + " Ron");
				}
			}
		});
		
		// Favorites Group
		this.removeProductFromFavorites.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tableFavorites.getSelection().length > 0) {
					TableItem selectedItem = tableFavorites.getSelection()[0];
					String productName = selectedItem.getText(0);
					tableFavorites.remove(tableFavorites.getSelectionIndex());
					
					// update the DB
					FavoritesDAO.INSTANCE.removeFavoriteProductFromUserWithId(productName, user.getId());
				}
			}
		});
		
		this.removeAllProductsFromFavorites.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tableFavorites.getItems().length > 0) {
					tableFavorites.removeAll();
					
					// update the DB
					FavoritesDAO.INSTANCE.removeAllFavoriteProductsFromUserWithId(user.getId());
				}
			}
		});
	}
	
	private int getAllProductsSum() {
		int sum = 0;
		
		for (int i = 0; i < tableBasket.getItems().length; i++) {
			TableItem item = tableBasket.getItems()[i];
			sum += Integer.parseInt(item.getText(1)) * Integer.parseInt(item.getText(2).replaceFirst(" Ron", ""));
		}
		
		return sum;
	}
	
}
