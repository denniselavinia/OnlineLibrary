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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.library.tables.User;
import com.library.tables.dao.BasketDAO;
import com.library.tables.dao.OrdersDAO;
import com.library.ui.MainWindow;
import com.library.ui.leftside.UserActions;

public class PlaceOrderDialog {
	
	private User user;
	private TableItem[] orderedItems;
	private MainWindow mainWindow;
	
	private Label lbFirstName;
	private Label lbLastName;
	private Text txtFirstName;
	private Text txtLastName;
	
	private Label lbEmail;
	private Text txtEmail;
	
	private Label lbPhoneNumer;
	private Text txtPhonenumber;
	
	private Label lbShippingAddress;
	private Text txtShippingAddress;
	
	private Label lbCity;
	private Text txtCity;
	
	private Label lbCounty;
	private Text txtCounty;
	
	private Label lbZipCode;
	private Text txtZipCode;
	
	private Label lbCountry;
	private Text txtCountry;
	
	private Button btnSubmit;
	
	private Label lbOrderDetails;
	private Text txtOrderDetails;
	private Label lblTtotalToPay;
	
	private Display display = Display.getDefault();
	private Shell shell = new Shell(display);
	
	public PlaceOrderDialog(User user, TableItem[] items, MainWindow mainWindow) {
		this.user = user;
		this.orderedItems = items;
		this.mainWindow = mainWindow;
		this.shell.setText("Dialog Plaseaza Comanda");
		this.shell.setLayout(new GridLayout(1, true));
		this.shell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.addUserInfo();
		this.addUserDetails();
		this.addOrderDetails();
		this.addSubmitButton();
		
		shell.pack();
		shell.open();
	}

	private void addUserInfo() {
		Label lbInfo = new Label(shell, SWT.NONE);
		lbInfo.setText("Va rugam introduceti informatiile necesare in toate campurile.\nCampurile marcate cu '*' sunt obligatorii.");
		
		Composite name = new Composite(shell, SWT.NONE);
		name.setLayout(new GridLayout(2, true));
		name.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		this.lbFirstName = new Label(name, SWT.NONE);
		this.lbFirstName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.lbFirstName.setText("Nume*:");
		
		this.lbLastName = new Label(name, SWT.NONE);
		this.lbLastName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		this.lbLastName.setText("Prenume*:");
		
		this.txtFirstName = new Text(name, SWT.BORDER);
		this.txtFirstName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.txtLastName = new Text(name, SWT.BORDER);
		this.txtLastName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		this.lbEmail = new Label(name, SWT.NONE);
		this.lbEmail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		this.lbEmail.setText("Adresa de Email*:");
		
		this.txtEmail = new Text(name, SWT.BORDER);
		this.txtEmail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		this.txtEmail.setText(this.user.getEmail());
		
		this.lbPhoneNumer = new Label(name, SWT.NONE);
		this.lbPhoneNumer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		this.lbPhoneNumer.setText("Numar de telefon*:");
		
		this.txtPhonenumber = new Text(name, SWT.BORDER);
		this.txtPhonenumber.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	}

	private void addUserDetails() {
		Composite details = new Composite(shell, SWT.NONE);
		details.setLayout(new GridLayout(2, true));
		details.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		this.lbShippingAddress = new Label(details, SWT.NONE);
		this.lbShippingAddress.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		this.lbShippingAddress.setText("Adresa de livrare*:");
		
		this.txtShippingAddress = new Text(details, SWT.BORDER);
		this.txtShippingAddress.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		this.lbCity = new Label(details, SWT.NONE);
		this.lbCity.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		this.lbCity.setText("Oras*:");
		
		this.lbCounty = new Label(details, SWT.NONE);
		this.lbCounty.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		this.lbCounty.setText("Judet*:");
		
		this.txtCity = new Text(details, SWT.BORDER);
		this.txtCity.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		this.txtCounty = new Text(details, SWT.BORDER);
		this.txtCounty.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		this.lbZipCode = new Label(details, SWT.NONE);
		this.lbZipCode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		this.lbZipCode.setText("Cod Postal");
		
		this.lbCountry = new Label(details, SWT.NONE);
		this.lbCountry.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		this.lbCountry.setText("Tara");
		
		this.txtZipCode = new Text(details, SWT.BORDER);
		this.txtZipCode.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		this.txtCountry = new Text(details, SWT.BORDER);
		this.txtCountry.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}
	
	private void addOrderDetails() {
		new Label(shell, SWT.NONE); // separator
		Label upperSeparator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
	    upperSeparator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
	    
	    lbOrderDetails = new Label(shell, SWT.NONE);
	    lbOrderDetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
	    lbOrderDetails.setText("Detalii comanda:");
	    
	    txtOrderDetails = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.READ_ONLY);
	    txtOrderDetails.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
	    String res = "";
	    int totalAmount = 0;
	    for (int i = 0; i < this.orderedItems.length; i++) {
	    	TableItem item = this.orderedItems[i];
	    	int quantity = Integer.parseInt(item.getText(1));
	    	int amount = quantity * Integer.parseInt(item.getText(2).replaceAll(" Ron", ""));
	    	totalAmount += amount;
	    	res += item.getText(0) + " (" + quantity  + "x" + item.getText(2) + ")\n";
	    }
	    txtOrderDetails.setText(res);
	    
	    lblTtotalToPay = new Label(shell, SWT.BOLD);
	    lblTtotalToPay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
	    lblTtotalToPay.setText("Total de plata: " + totalAmount + " Ron\nPlata se va face ramburs, la primirea coletului!");
	    
	    new Label(shell, SWT.NONE); // separator
		Label downSeparator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
	    downSeparator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
	}
	
	private void addSubmitButton() {
		this.btnSubmit = new Button(shell, SWT.PUSH);
		this.btnSubmit.setLayoutData((new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1)));
		this.btnSubmit.setText("Plaseaza comanda!");
		this.btnSubmit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (!checkOrderDetails()) return;
				String orderContent = txtOrderDetails.getText() + "\n"
						+ lblTtotalToPay.getText();
				
				if (OrdersDAO.INSTANCE.insertOrderForUser(orderContent, user.getId()) != -1) {
					showMessageBox("Comanda a fost plasata cu succes!\nUn email de confirmare a fost trimis la adresa de email " + txtEmail.getText(), SWT.ICON_INFORMATION);
					
					// empty the basket for the user
					BasketDAO.INSTANCE.removeBasketForUserId(user.getId());
					
					// refresh the order history and basket tabs
					mainWindow.getLeftSide().populateOrderHistoryTable();
					
					if (mainWindow.getRightSide() instanceof UserActions)
						((UserActions)mainWindow.getRightSide()).populateBasketTable();
					shell.dispose();
				} else {
					showMessageBox("A aparut o eroare! Comanda nu a putut fi plasata cu succes!", SWT.ICON_WARNING);
				}
			}
		});
	}
	
	private boolean checkOrderDetails() {
		if (this.txtFirstName.getText().isBlank()){
			showMessageBox("Introduceti numele.", SWT.ICON_WARNING);
			return false;
		}
		
		if (this.txtLastName.getText().isBlank()){
			showMessageBox("Introduceti prenumele.", SWT.ICON_WARNING);
			return false;
		}
		
		if (this.txtEmail.getText().isBlank()){
			showMessageBox("Introduceti adresa de email.", SWT.ICON_WARNING);
			return false;
		}
		
		if (!this.txtEmail.getText().contains("@")){
			showMessageBox("Adresa de email invalida.", SWT.ICON_WARNING);
			return false;
		}
		
		if (this.txtPhonenumber.getText().isBlank()){
			showMessageBox("Introduceti numarul de telefon.", SWT.ICON_WARNING);
			return false;
		}
		
		if (this.txtPhonenumber.getText().length() != 10){
			showMessageBox("Numar de telefon invalid.", SWT.ICON_WARNING);
			return false;
		}
		
		for (int i = 0; i < this.txtPhonenumber.getText().length(); i++) {
			char c = this.txtPhonenumber.getText().charAt(i);
			if (c < '0' || c > '9') {
				showMessageBox("Numarul de telefon trebuie sa contina doar cifre.", SWT.ICON_WARNING);
				return false;
			}
		}
		
		if (this.txtShippingAddress.getText().isBlank()){
			showMessageBox("Introduceti adresa de livrare.", SWT.ICON_WARNING);
			return false;
		}
		
		if (this.txtCity.getText().isBlank()){
			showMessageBox("Introduceti orasul.", SWT.ICON_WARNING);
			return false;
		}
		
		if (this.txtCounty.getText().isBlank()){
			showMessageBox("Introduceti judetul.", SWT.ICON_WARNING);
			return false;
		}
		
		return true;
	}
	
	private void showMessageBox(String message, int type) {
		MessageBox messageBox = new MessageBox(shell, SWT.OK | type | SWT.CANCEL);
		messageBox.setMessage(message);
		messageBox.open();
	}

}
