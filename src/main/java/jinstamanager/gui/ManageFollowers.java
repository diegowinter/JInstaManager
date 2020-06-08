package jinstamanager.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

import jinstamanager.instagram.requests.GetFollowers;
import jinstamanager.instagram.requests.GetFollowing;
import jinstamanager.instagram.requests.GetProfile;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ManageFollowers extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JTextField textFieldUserSearch;
	private static InstagramUser currentUser;
	private static Instagram4j instagram;
	private static JList<String> listFollowers;
	private static JList<String> listFollowing;
	private static JList<String> listNaoSeguemDeVolta;
	
	private GetProfile getProfile;
	private GetFollowers getFollowers;
	private GetFollowing getFollowing;
	
	private static List<InstagramUserSummary> userFollowersList;
	private static List<InstagramUserSummary> userFollowingList;
	private static List<InstagramUserSummary> userNSDVList = new ArrayList<InstagramUserSummary>();
	private static DefaultListModel<String> modelFollowers = new DefaultListModel<String>();
	private static DefaultListModel<String> modelFollowing = new DefaultListModel<String>();
	private static DefaultListModel<String> modelNSDV = new DefaultListModel<String>();

	public ManageFollowers() {
		
		getProfile = new GetProfile();
		getFollowers = new GetFollowers();
		getFollowing = new GetFollowing();
		
		this.setBounds(0, 0, 900, 500);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300, 186, 0, 300, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 300, 53, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component horizontalStrutSB1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrutSB1 = new GridBagConstraints();
		gbc_horizontalStrutSB1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrutSB1.gridx = 0;
		gbc_horizontalStrutSB1.gridy = 1;
		add(horizontalStrutSB1, gbc_horizontalStrutSB1);
		
		textFieldUserSearch = new JTextField();
		textFieldUserSearch.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_textFieldUserSearch = new GridBagConstraints();
		gbc_textFieldUserSearch.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUserSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUserSearch.gridx = 1;
		gbc_textFieldUserSearch.gridy = 1;
		add(textFieldUserSearch, gbc_textFieldUserSearch);
		textFieldUserSearch.setColumns(10);
		
		JButton btnAnalisarPerfil = new JButton("Analisar perfil");
		btnAnalisarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(instagram.getUsername());
				if(currentUser.getUsername().equals(textFieldUserSearch.getText())) {
					userFollowersList = getFollowers.getFollowersList(instagram, currentUser);
					userFollowingList = getFollowing.getFollowingList(instagram, currentUser);
				} else {
					userFollowersList = getFollowers.getFollowersList(instagram,
							getProfile.getProfile(instagram, textFieldUserSearch.getText()));
					userFollowingList = getFollowing.getFollowingList(instagram,
							getProfile.getProfile(instagram, textFieldUserSearch.getText()));
				}
				updateFollowersList(userFollowersList);
				updateFollowingList(userFollowingList);
				fetchNSDVList(userFollowersList, userFollowingList);
				updateNSDVList(userNSDVList);
			}
		});
		btnAnalisarPerfil.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_btnAnalisarPerfil = new GridBagConstraints();
		gbc_btnAnalisarPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnalisarPerfil.gridx = 2;
		gbc_btnAnalisarPerfil.gridy = 1;
		add(btnAnalisarPerfil, gbc_btnAnalisarPerfil);
		
		Component horizontalStrutSB2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrutSB2 = new GridBagConstraints();
		gbc_horizontalStrutSB2.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrutSB2.gridx = 3;
		gbc_horizontalStrutSB2.gridy = 1;
		add(horizontalStrutSB2, gbc_horizontalStrutSB2);
		
		JLabel lblSeguidores = new JLabel("Seguidores");
		lblSeguidores.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblSeguidores = new GridBagConstraints();
		gbc_lblSeguidores.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidores.gridx = 0;
		gbc_lblSeguidores.gridy = 3;
		add(lblSeguidores, gbc_lblSeguidores);
		
		JLabel lblSeguindo = new JLabel("Seguindo");
		lblSeguindo.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblSeguindo = new GridBagConstraints();
		gbc_lblSeguindo.gridwidth = 2;
		gbc_lblSeguindo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguindo.gridx = 1;
		gbc_lblSeguindo.gridy = 3;
		add(lblSeguindo, gbc_lblSeguindo);
		
		JLabel lblNaoSeguemDeVolta = new JLabel("Não seguem de volta");
		lblNaoSeguemDeVolta.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNaoSeguemDeVolta = new GridBagConstraints();
		gbc_lblNaoSeguemDeVolta.insets = new Insets(0, 0, 5, 0);
		gbc_lblNaoSeguemDeVolta.gridx = 3;
		gbc_lblNaoSeguemDeVolta.gridy = 3;
		add(lblNaoSeguemDeVolta, gbc_lblNaoSeguemDeVolta);
		
		// Followers List
		listFollowers = new JList<String>();
		listFollowers.setModel(modelFollowers);
		JScrollPane scrollFollowers = new JScrollPane();
		GridBagConstraints gbc_scrollFollowers = new GridBagConstraints();
		gbc_scrollFollowers.insets = new Insets(0, 0, 5, 5);
		gbc_scrollFollowers.fill = GridBagConstraints.BOTH;
		gbc_scrollFollowers.gridx = 0;
		gbc_scrollFollowers.gridy = 4;
		add(scrollFollowers, gbc_scrollFollowers);
		scrollFollowers.setViewportView(listFollowers);
		
		// Following List
		listFollowing = new JList<String>();
		listFollowers.setModel(modelFollowing);
		JScrollPane scrollFollowing = new JScrollPane();
		GridBagConstraints gbc_scrollFollowing = new GridBagConstraints();
		gbc_scrollFollowing.gridwidth = 2;
		gbc_scrollFollowing.insets = new Insets(0, 0, 5, 5);
		gbc_scrollFollowing.fill = GridBagConstraints.BOTH;
		gbc_scrollFollowing.gridx = 1;
		gbc_scrollFollowing.gridy = 4;
		add(scrollFollowing, gbc_scrollFollowing);
		scrollFollowing.setViewportView(listFollowing);
		
		// "Não seguem de volta" List
		listNaoSeguemDeVolta = new JList<String>();
		listFollowers.setModel(modelNSDV);
		JScrollPane scrollNaoSeguemDeVolta = new JScrollPane();
		GridBagConstraints gbc_scrollNaoSeguemDeVolta = new GridBagConstraints();
		gbc_scrollNaoSeguemDeVolta.insets = new Insets(0, 0, 5, 0);
		gbc_scrollNaoSeguemDeVolta.fill = GridBagConstraints.BOTH;
		gbc_scrollNaoSeguemDeVolta.gridx = 3;
		gbc_scrollNaoSeguemDeVolta.gridy = 4;
		add(scrollNaoSeguemDeVolta, gbc_scrollNaoSeguemDeVolta);
		scrollNaoSeguemDeVolta.setViewportView(listNaoSeguemDeVolta);
		
		JButton btnTxtSummaryFollowers = new JButton("Gerar relatório (.txt)");
		btnTxtSummaryFollowers.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_btnTxtSummaryFollowers = new GridBagConstraints();
		gbc_btnTxtSummaryFollowers.insets = new Insets(0, 0, 0, 5);
		gbc_btnTxtSummaryFollowers.gridx = 0;
		gbc_btnTxtSummaryFollowers.gridy = 5;
		add(btnTxtSummaryFollowers, gbc_btnTxtSummaryFollowers);
		
		JButton btnTxtSummaryFollowing = new JButton("Gerar relatório (.txt)");
		btnTxtSummaryFollowing.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_btnTxtSummaryFollowing = new GridBagConstraints();
		gbc_btnTxtSummaryFollowing.gridwidth = 2;
		gbc_btnTxtSummaryFollowing.insets = new Insets(0, 0, 0, 5);
		gbc_btnTxtSummaryFollowing.gridx = 1;
		gbc_btnTxtSummaryFollowing.gridy = 5;
		add(btnTxtSummaryFollowing, gbc_btnTxtSummaryFollowing);
		
		JButton btnTxtSummaryNaoSeguemDeVolta = new JButton("Gerar relatório (.txt)");
		btnTxtSummaryNaoSeguemDeVolta.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_btnTxtSummaryNaoSeguemDeVolta = new GridBagConstraints();
		gbc_btnTxtSummaryNaoSeguemDeVolta.gridx = 3;
		gbc_btnTxtSummaryNaoSeguemDeVolta.gridy = 5;
		add(btnTxtSummaryNaoSeguemDeVolta, gbc_btnTxtSummaryNaoSeguemDeVolta);
		
	}
	
	public static void setUsername(String username) {
		textFieldUserSearch.setText(username);
	}
	
	public static void setCurrentUser(InstagramUser user) {
		currentUser = user;
	}
	
	public static void setInstagram4jInstance(Instagram4j instagram4j) {
		instagram = instagram4j;
	}
	
	static void updateFollowersList(List<InstagramUserSummary> users) {
		modelFollowers.clear();
		Vector<String> elements = new Vector<String>();
		for (InstagramUserSummary user : users) {
			String element = user.full_name + " (@" + user.getUsername() + ")";
			elements.add(element);
		}
		listFollowers.setListData(elements);
	}
	
	static void updateFollowingList(List<InstagramUserSummary> users) {
		modelFollowing.clear();
		Vector<String> elements = new Vector<String>();
		for (InstagramUserSummary user : users) {
			String element = user.full_name + " (@" + user.getUsername() + ")";
			elements.add(element);
		}
		listFollowing.setListData(elements);
	}
	
	static List<InstagramUserSummary> fetchNSDVList(List<InstagramUserSummary> followers, List<InstagramUserSummary> following) {
		userNSDVList.clear();
		boolean found = false;
		for (InstagramUserSummary userFollowing : following) {
			String user1 = userFollowing.getUsername();
			for (InstagramUserSummary userFollower : followers) {
				String user2 = userFollower.getUsername();
				if(user1.equals(user2)) {
					found = true;
				}
			}
			if(found == false) {
				userNSDVList.add(userFollowing);
			} else {
				found = false;
			}
		}
		
		return userNSDVList;
	}
	
	static void updateNSDVList(List<InstagramUserSummary> users) {
		modelNSDV.clear();
		Vector<String> elements = new Vector<String>();
		for (InstagramUserSummary user : users) {
			String element = user.full_name + " (@" + user.getUsername() + ")";
			elements.add(element);
		}
		listNaoSeguemDeVolta.setListData(elements);
	}

}
