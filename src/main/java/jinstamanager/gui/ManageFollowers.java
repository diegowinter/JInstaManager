package jinstamanager.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

import jinstamanager.instagram.requests.GetFollowers;
import jinstamanager.instagram.requests.GetFollowing;
import jinstamanager.instagram.requests.GetProfile;
import jinstamanager.util.GenerateReportPdf;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class ManageFollowers extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JTextField textFieldUserSearch;
	private static InstagramUser connectedUser;
	private static InstagramUser currentUser;
	private static Instagram4j instagram;

	private static JList<String> listFollowers;
	private static JList<String> listFollowing;
	private static JList<String> listNaoSeguemDeVolta;
	private static JList<String> listNaoSigoDeVolta;

	private static List<InstagramUserSummary> userFollowersList;
	private static List<InstagramUserSummary> userFollowingList;
	private static List<InstagramUserSummary> userNSDVList = new ArrayList<InstagramUserSummary>();
	private static List<InstagramUserSummary> userNSDV2List = new ArrayList<InstagramUserSummary>();

	private static DefaultListModel<String> modelFollowers = new DefaultListModel<String>();
	private static DefaultListModel<String> modelFollowing = new DefaultListModel<String>();
	private static DefaultListModel<String> modelNSDV = new DefaultListModel<String>();
	private static DefaultListModel<String> modelNSDV2 = new DefaultListModel<String>();

	private static JLabel label;
	private static JLabel label_1;
	private static JLabel label_2;
	private static JLabel label_4;

	private GetProfile getProfile;
	private GetFollowers getFollowers;
	private GetFollowing getFollowing;

	public ManageFollowers() {

		getProfile = new GetProfile();
		getFollowers = new GetFollowers();
		getFollowing = new GetFollowing();

		this.setBounds(0, 0, 800, 500);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 200, 200, 200, 200, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 300, 20, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
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
		gbc_textFieldUserSearch.gridwidth = 2;
		gbc_textFieldUserSearch.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldUserSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUserSearch.gridx = 1;
		gbc_textFieldUserSearch.gridy = 1;
		add(textFieldUserSearch, gbc_textFieldUserSearch);
		textFieldUserSearch.setColumns(10);

		Component horizontalStrutSB2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrutSB2 = new GridBagConstraints();
		gbc_horizontalStrutSB2.fill = GridBagConstraints.HORIZONTAL;
		gbc_horizontalStrutSB2.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrutSB2.gridx = 3;
		gbc_horizontalStrutSB2.gridy = 1;
		add(horizontalStrutSB2, gbc_horizontalStrutSB2);

		JButton btnAnalisarPerfil = new JButton("Analisar perfil");
		btnAnalisarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstagramUser user;
				if (connectedUser.getUsername().equals(textFieldUserSearch.getText())) {
					userFollowersList = getFollowers.getFollowersList(instagram, connectedUser);
					userFollowingList = getFollowing.getFollowingList(instagram, connectedUser);
					currentUser = connectedUser;
				} else {
					user = getProfile.getProfile(instagram, textFieldUserSearch.getText());
					userFollowersList = getFollowers.getFollowersList(instagram, user);
					userFollowingList = getFollowing.getFollowingList(instagram, user);
					currentUser = user;
				}
				updateFollowersList(userFollowersList);
				updateFollowingList(userFollowingList);
				fetchNSDVList(userFollowersList, userFollowingList);
				updateNSDVList(userNSDVList);
				fetchNSDV2List(userFollowersList, userFollowingList);
				updateNSDV2List(userNSDV2List);
				label.setText(String.valueOf(userFollowersList.size()));
				label_1.setText(String.valueOf(userFollowingList.size()));
				label_4.setText(String.valueOf(userNSDVList.size()));
				label_2.setText(String.valueOf(userNSDV2List.size()));
			}
		});
		btnAnalisarPerfil.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_btnAnalisarPerfil = new GridBagConstraints();
		gbc_btnAnalisarPerfil.gridwidth = 2;
		gbc_btnAnalisarPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnalisarPerfil.gridx = 1;
		gbc_btnAnalisarPerfil.gridy = 2;
		add(btnAnalisarPerfil, gbc_btnAnalisarPerfil);

		JLabel lblSeguidores = new JLabel("Seguidores");
		lblSeguidores.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblSeguidores = new GridBagConstraints();
		gbc_lblSeguidores.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidores.gridx = 0;
		gbc_lblSeguidores.gridy = 4;
		add(lblSeguidores, gbc_lblSeguidores);

		JLabel lblSeguindo = new JLabel("Seguindo");
		lblSeguindo.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblSeguindo = new GridBagConstraints();
		gbc_lblSeguindo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguindo.gridx = 1;
		gbc_lblSeguindo.gridy = 4;
		add(lblSeguindo, gbc_lblSeguindo);

		JLabel lblNoSigonoSeguem = new JLabel("Não seguem de volta");
		lblNoSigonoSeguem.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNoSigonoSeguem = new GridBagConstraints();
		gbc_lblNoSigonoSeguem.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoSigonoSeguem.gridx = 2;
		gbc_lblNoSigonoSeguem.gridy = 4;
		add(lblNoSigonoSeguem, gbc_lblNoSigonoSeguem);

		JLabel lblNoSigoDe = new JLabel("Não sigo de volta");
		lblNoSigoDe.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblNoSigoDe = new GridBagConstraints();
		gbc_lblNoSigoDe.insets = new Insets(0, 0, 5, 0);
		gbc_lblNoSigoDe.gridx = 3;
		gbc_lblNoSigoDe.gridy = 4;
		add(lblNoSigoDe, gbc_lblNoSigoDe);

		label = new JLabel("0");
		label.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 5;
		add(label, gbc_label);

		label_1 = new JLabel("0");
		label_1.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 5;
		add(label_1, gbc_label_1);

		label_4 = new JLabel("0");
		label_4.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 2;
		gbc_label_4.gridy = 5;
		add(label_4, gbc_label_4);

		label_2 = new JLabel("0");
		label_2.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 0);
		gbc_label_2.gridx = 3;
		gbc_label_2.gridy = 5;
		add(label_2, gbc_label_2);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 4;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 6;
		add(tabbedPane, gbc_tabbedPane);

		// Followers List
		listFollowers = new JList<String>();
		listFollowers.setModel(modelFollowers);
		JScrollPane scrollFollowers = new JScrollPane();
		scrollFollowers.setViewportView(listFollowers);
		tabbedPane.addTab("Seguidores", null, scrollFollowers, "Veja quem segue o perfil buscado.");

		// Following List
		listFollowing = new JList<String>();
		listFollowing.setModel(modelFollowing);
		JScrollPane scrollFollowing = new JScrollPane();
		scrollFollowing.setViewportView(listFollowing);
		tabbedPane.addTab("Seguindo", null, scrollFollowing, "Veja quem o perfil buscado segue.");

		// "Não seguem de volta" List
		listNaoSeguemDeVolta = new JList<String>();
		listNaoSeguemDeVolta.setModel(modelNSDV);
		JScrollPane scrollNaoSeguemDeVolta = new JScrollPane();
		scrollNaoSeguemDeVolta.setViewportView(listNaoSeguemDeVolta);
		tabbedPane.addTab("Não seguem de volta", null, scrollNaoSeguemDeVolta,
				"Veja quem não segue de volta o perfil buscado.");

		// "Não sigo de volta" List
		listNaoSigoDeVolta = new JList<String>();
		listNaoSigoDeVolta.setModel(modelNSDV2);
		JScrollPane scrollNaoSigoDeVolta = new JScrollPane();
		scrollNaoSigoDeVolta.setViewportView(listNaoSigoDeVolta);
		tabbedPane.addTab("Não sigo de volta", null, scrollNaoSigoDeVolta,
				"Veja quem o perfil buscado não segue de volta.");

		JButton btnTxtSummaryNaoSeguemDeVolta = new JButton("Gerar relatório");
		btnTxtSummaryNaoSeguemDeVolta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = choosePath();
				GenerateReportPdf.generateReport(file, userFollowersList, userFollowingList, userNSDVList, userNSDV2List,
						currentUser.getUsername());
			}
		});
		btnTxtSummaryNaoSeguemDeVolta.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_btnTxtSummaryNaoSeguemDeVolta = new GridBagConstraints();
		gbc_btnTxtSummaryNaoSeguemDeVolta.anchor = GridBagConstraints.EAST;
		gbc_btnTxtSummaryNaoSeguemDeVolta.gridx = 3;
		gbc_btnTxtSummaryNaoSeguemDeVolta.gridy = 7;
		add(btnTxtSummaryNaoSeguemDeVolta, gbc_btnTxtSummaryNaoSeguemDeVolta);

	}

	public static void setUsername(String username) {
		textFieldUserSearch.setText(username);
	}

	public static void setCurrentUser(InstagramUser user) {
		connectedUser = user;
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

	static List<InstagramUserSummary> fetchNSDVList(List<InstagramUserSummary> followers,
			List<InstagramUserSummary> following) {
		userNSDVList.clear();
		boolean found = false;
		for (InstagramUserSummary userFollowing : following) {
			String user1 = userFollowing.getUsername();
			for (InstagramUserSummary userFollower : followers) {
				String user2 = userFollower.getUsername();
				if (user1.equals(user2)) {
					found = true;
				}
			}
			if (found == false) {
				userNSDVList.add(userFollowing);
			} else {
				found = false;
			}
		}

		return userNSDVList;
	}

	static List<InstagramUserSummary> fetchNSDV2List(List<InstagramUserSummary> followers,
			List<InstagramUserSummary> following) {
		userNSDV2List.clear();
		boolean found = false;
		for (InstagramUserSummary userFollowing : followers) {
			String user1 = userFollowing.getUsername();
			for (InstagramUserSummary userFollower : following) {
				String user2 = userFollower.getUsername();
				if (user1.equals(user2)) {
					found = true;
				}
			}
			if (found == false) {
				userNSDV2List.add(userFollowing);
			} else {
				found = false;
			}
		}

		return userNSDV2List;
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

	static void updateNSDV2List(List<InstagramUserSummary> users) {
		modelNSDV2.clear();
		Vector<String> elements = new Vector<String>();
		for (InstagramUserSummary user : users) {
			String element = user.full_name + " (@" + user.getUsername() + ")";
			elements.add(element);
		}
		listNaoSigoDeVolta.setListData(elements);
	}

	public static File choosePath() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Salvar como");
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setApproveButtonText("Salvar");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivo PDF (*.pdf)", "pdf"));
		fileChooser.setBackground(Color.WHITE);
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File dir = fileChooser.getSelectedFile();
			return dir;
		}
		
		return null;
	}

}
