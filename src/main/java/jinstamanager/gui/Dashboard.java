package jinstamanager.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

public class Dashboard extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	Profile profile;
	ManageFollowers manageFollowers;

	public Dashboard() {
		
		this.setBounds(0, 0, 900, 500);
		setLayout(new BorderLayout(0, 0));
		profile = new Profile();
		manageFollowers = new ManageFollowers();
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		tabbedPane.addTab("Perfil", null, profile, "Veja o seu perfil ou o de algum usuário público no Instagram.");
		tabbedPane.addTab("Gerenciar seguidores", null, manageFollowers, "Gerencie os seus seguidores ou colete dados de contas públicas no Instagram.");

	}

}
