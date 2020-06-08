package jinstamanager.gui;

import javax.swing.JPanel;
import java.awt.CardLayout;

public class Content extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static Login login;
	private static Dashboard dashboard;

	public Content() {
		this.setBounds(0, 0, 900, 500);
		setLayout(new CardLayout(0, 0));
		
		login = new Login();
		this.add(login);
		
		dashboard = new Dashboard();
		this.add(dashboard);		
		
		
	}
	
	public static void alternateScreens() {
		if(login.isVisible()) {
			login.setVisible(false);
			dashboard.setVisible(true);
		} else {
			login.setVisible(true);
			dashboard.setVisible(false);
		}
	}

}
