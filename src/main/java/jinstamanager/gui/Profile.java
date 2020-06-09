package jinstamanager.gui;

import javax.swing.JPanel;

import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramGetMediaInfoRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedItem;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetMediaInfoResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUser;

import jinstamanager.gui.util.StretchIcon;
import jinstamanager.instagram.requests.GetProfile;

import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Profile extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static JLabel lblRealUsersName;
	private static JLabel lblusername;
	private static JLabel lblFollowersCount;
	private static JLabel lblFollowingCount;
	private static JLabel lblPostsCount;
	private static JLabel lblProfilePicture;
	private static JTextField textField;
	private JButton btnExibirPerfil;
	private static String currentUsername;
	
	private static Instagram4j instagram;
	private GetProfile getProfile;
	private JScrollPane scrollPaneBiography;
	private static JTextPane textPaneBiography;
	
	private static InstagramUser currentUser;

	public Profile() {
		
		getProfile = new GetProfile();
		
		ImageIcon profilePlaceholder = new ImageIcon("res/profile.png");
		
		this.setBounds(0, 0, 800, 500);
		
		// GridBagLayout settings
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300, 100, 100, 100, 300, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		textField = new JTextField();
		textField.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		btnExibirPerfil = new JButton("Exibir perfil");
		btnExibirPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().equals(currentUsername)) {
					InstagramUser user = null;
					user = getProfile.getProfile(instagram, textField.getText());
					if(user != null) {
						updateContent(user);
					}
				}
			}
		});
		btnExibirPerfil.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_btnExibirPerfil = new GridBagConstraints();
		gbc_btnExibirPerfil.insets = new Insets(0, 0, 5, 5);
		gbc_btnExibirPerfil.gridx = 3;
		gbc_btnExibirPerfil.gridy = 1;
		add(btnExibirPerfil, gbc_btnExibirPerfil);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 0;
		gbc_horizontalStrut_1.gridy = 2;
		add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		// Profile picture Label with GridBag constraints
		lblProfilePicture = new JLabel();
		GridBagConstraints gbc_lblProfilePicture = new GridBagConstraints();
		gbc_lblProfilePicture.gridwidth = 3;
		gbc_lblProfilePicture.insets = new Insets(0, 0, 5, 5);
		gbc_lblProfilePicture.gridx = 1;
		gbc_lblProfilePicture.gridy = 2;
		add(lblProfilePicture, gbc_lblProfilePicture);
		lblProfilePicture.setPreferredSize(new Dimension(150, 150));
		lblProfilePicture.setIcon(new StretchIcon(profilePlaceholder.getImage(), false));
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut.gridx = 4;
		gbc_horizontalStrut.gridy = 2;
		add(horizontalStrut, gbc_horizontalStrut);
		
		// Name of the User
		lblRealUsersName = new JLabel("Real User's Name");
		lblRealUsersName.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblRealUsersName = new GridBagConstraints();
		gbc_lblRealUsersName.gridwidth = 3;
		gbc_lblRealUsersName.insets = new Insets(0, 0, 5, 5);
		gbc_lblRealUsersName.gridx = 1;
		gbc_lblRealUsersName.gridy = 3;
		add(lblRealUsersName, gbc_lblRealUsersName);
		
		// @Username of the User
		lblusername = new JLabel("@username");
		lblusername.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblusername = new GridBagConstraints();
		gbc_lblusername.gridwidth = 3;
		gbc_lblusername.insets = new Insets(0, 0, 5, 5);
		gbc_lblusername.gridx = 1;
		gbc_lblusername.gridy = 4;
		add(lblusername, gbc_lblusername);
		
		// Followers count
		lblFollowersCount = new JLabel("<followers>");
		lblFollowersCount.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblFollowersCount = new GridBagConstraints();
		gbc_lblFollowersCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblFollowersCount.gridx = 1;
		gbc_lblFollowersCount.gridy = 5;
		add(lblFollowersCount, gbc_lblFollowersCount);
		
		// Following count
		lblFollowingCount = new JLabel("<following>");
		lblFollowingCount.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblFollowingCount = new GridBagConstraints();
		gbc_lblFollowingCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblFollowingCount.gridx = 2;
		gbc_lblFollowingCount.gridy = 5;
		add(lblFollowingCount, gbc_lblFollowingCount);
		
		// Posts count
		lblPostsCount = new JLabel("<posts>");
		lblPostsCount.setFont(new Font("Nirmala UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblPostsCount = new GridBagConstraints();
		gbc_lblPostsCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblPostsCount.gridx = 3;
		gbc_lblPostsCount.gridy = 5;
		add(lblPostsCount, gbc_lblPostsCount);
		
		JLabel lblSeguidores = new JLabel("Seguidores");
		lblSeguidores.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblSeguidores = new GridBagConstraints();
		gbc_lblSeguidores.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguidores.gridx = 1;
		gbc_lblSeguidores.gridy = 6;
		add(lblSeguidores, gbc_lblSeguidores);
		
		JLabel lblSeguindo = new JLabel("Seguindo");
		lblSeguindo.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblSeguindo = new GridBagConstraints();
		gbc_lblSeguindo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeguindo.gridx = 2;
		gbc_lblSeguindo.gridy = 6;
		add(lblSeguindo, gbc_lblSeguindo);
		
		JLabel lblPublicaes = new JLabel("Publicações");
		lblPublicaes.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		GridBagConstraints gbc_lblPublicaes = new GridBagConstraints();
		gbc_lblPublicaes.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublicaes.gridx = 3;
		gbc_lblPublicaes.gridy = 6;
		add(lblPublicaes, gbc_lblPublicaes);
		
		scrollPaneBiography = new JScrollPane();
		GridBagConstraints gbc_scrollPaneBiography = new GridBagConstraints();
		gbc_scrollPaneBiography.gridwidth = 3;
		gbc_scrollPaneBiography.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPaneBiography.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneBiography.gridx = 1;
		gbc_scrollPaneBiography.gridy = 7;
		add(scrollPaneBiography, gbc_scrollPaneBiography);
		
		textPaneBiography = new JTextPane();
		textPaneBiography.setFont(new Font("Nirmala UI", Font.PLAIN, 12));
		scrollPaneBiography.setViewportView(textPaneBiography);
		
	}
	
	public static void updateContent(InstagramUser user) {
		currentUser = user;
		currentUsername = user.getUsername();
		textField.setText(user.getUsername());
		try {
			lblProfilePicture.setIcon(getUserProfilePhoto(user.getProfile_pic_url()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		lblRealUsersName.setText(user.getFull_name());
		lblusername.setText("@" + user.getUsername());
		lblFollowersCount.setText(String.valueOf(user.getFollower_count()));
		lblFollowingCount.setText(String.valueOf(user.getFollowing_count()));
		lblPostsCount.setText(String.valueOf(user.getMedia_count()));
		textPaneBiography.setText(user.getBiography());
	}
	
	public static ImageIcon getUserProfilePhoto(String imgUrl) throws IOException {
		URL url = new URL(imgUrl);		
		BufferedImage bufferedImage = ImageIO.read(url);
		//Image image = bufferedImage.getScaledInstance(280, 280, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(bufferedImage);
		
		return imageIcon;
	}
	
	public static void setInstagram4jInstance(Instagram4j instagram4j) {
		instagram = instagram4j;
	}

}
