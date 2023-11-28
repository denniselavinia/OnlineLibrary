package com.library.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.library.tables.User;
import com.library.ui.leftside.AdminActions;
import com.library.ui.leftside.UserActions;
import com.library.ui.rightside.UserTabs;
import com.library.utils.ImageHandler;

public class MainWindow {
	
	private User user;
	
	private Display display = Display.getDefault();
	private Shell shell = new Shell(display);
	
	private UserTabs leftComposite;
	private Composite rightComposite;
	
	public MainWindow(User user) {
		this.user = user;
		initMainWindow();
	}

	private void initMainWindow() {
		shell.setText("Librarie Online - Utilizator: " + user.getUsername() + (user.isAdmin() ? " (administrator)" : ""));
		shell.setMinimumSize(300, 600);
		shell.setLayout(new GridLayout(2, false));
		shell.setImage(ImageHandler.IMAGE_BOOK);
		
		this.leftComposite = new UserTabs(this, user, shell, SWT.NONE);
		this.rightComposite = user.isAdmin() ? new AdminActions(this, user, shell, SWT.NONE) : new UserActions(this, user, shell, SWT.NONE);
		
		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	public UserTabs getLeftSide() {
		return this.leftComposite;
	}
	
	public Composite getRightSide() {
		return this.rightComposite;
	}

}
