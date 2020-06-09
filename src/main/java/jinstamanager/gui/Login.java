package jinstamanager.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;

import jinstamanager.gui.util.TextPrompt;
import jinstamanager.instagram.requests.GetProfile;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JPasswordField passwordField;
	
	private jinstamanager.instagram.Login instagramLogin;
	private GetProfile getProfile;

	public Login() {
		
		//ImageIcon iconUser = new ImageIcon("res/icons/user.png");
		//ImageIcon iconPassword = new ImageIcon("res/icons/lock.png");
		
		instagramLogin = new jinstamanager.instagram.Login();
		getProfile = new GetProfile();
		
		this.setBounds(0, 0, 800, 500);
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		this.add(panel);
		
		
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);
		
		JPanel panelCredentials = new JPanel();
		panelCredentials.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel.add(panelCredentials);
		panelCredentials.setBounds(0, 0, 300, 200);
		panelCredentials.setPreferredSize(new Dimension(300, 175));
		
		panelCredentials.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(30, 35, 240, 25);
		
		
		TextPrompt textPrompt1 = new TextPrompt("Nome de usuário", textField);
		textPrompt1.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		textPrompt1.setForeground(Color.DARK_GRAY);
		textPrompt1.changeAlpha(0.5f);
		textField.setMargin(new Insets(0, 5, 0, 5));
		
		panelCredentials.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(30, 70, 240, 25);
		
		
		TextPrompt textPrompt2 = new TextPrompt("Senha", passwordField);
		textPrompt2.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		textPrompt2.setForeground(Color.DARK_GRAY);
		textPrompt2.changeAlpha(0.5f);
		passwordField.setMargin(new Insets(0, 5, 0, 5));
		panelCredentials.add(passwordField);
		
		JButton btnEntrarNaSua = new JButton("Entrar na sua conta Instagram");
		btnEntrarNaSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Instagram4j instagram = instagramLogin.login(textField.getText(), passwordField.getText());
				if(instagram != null) {
					InstagramUser user = getProfile.getConnectedProfile(instagram);
					if(user != null) {
						Profile.updateContent(user);
						Content.alternateScreens();
						ManageFollowers.setUsername(user.getUsername());
						ManageFollowers.setCurrentUser(user);
						ManageFollowers.setInstagram4jInstance(instagram);
						Profile.setInstagram4jInstance(instagram);
					}
				}
			}
		});
		btnEntrarNaSua.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		btnEntrarNaSua.setBounds(50, 120, 200, 25);
		panelCredentials.add(btnEntrarNaSua);
		
	}
}
