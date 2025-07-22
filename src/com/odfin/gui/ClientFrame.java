package com.odfin.gui;

import com.odfin.domain.User;
import com.odfin.network.rest.RestNetworkClient;
import com.odfin.gui.listener.MessageListener;
import com.odfin.gui.listener.StatusListener;
import com.odfin.gui.listener.UserListListener;

import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame {
	private final RestNetworkClient client;
	private final User currentUser;

	private JTextArea chatArea;
	private JTextField inputField;
	private JList<User> userList;

	public ClientFrame(RestNetworkClient client, User currentUser, java.util.List<User> users) {
		super("Chat - " + currentUser);
		this.client = client;
		this.currentUser = currentUser;
		initComponents(users);
		attachListeners();
	}

	private void initComponents(java.util.List<User> users) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Chatbereich
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		add(new JScrollPane(chatArea), BorderLayout.CENTER);

		// Eingabefeld
		inputField = new JTextField();
		add(inputField, BorderLayout.SOUTH);

		// Nutzerliste
		userList = new JList<>(new DefaultListModel<>());
		DefaultListModel<User> model = (DefaultListModel<User>) userList.getModel();
		users.forEach(model::addElement);
		add(new JScrollPane(userList), BorderLayout.WEST);

		setSize(600, 400);
		setLocationRelativeTo(null);
	}

	private void attachListeners() {
		inputField.addActionListener(new MessageListener(client, inputField, chatArea, currentUser));
		addWindowListener(new StatusListener(client, currentUser));
		userList.addListSelectionListener(new UserListListener(userList, client));
	}

	// Methode zum initialen Laden aller Nachrichten
	public void loadChatHistory() {
		chatArea.setText("");
		try {
			client.getMessages().forEach(m -> chatArea.append(m.getOwner().getUsername()) + ": " + m.getPayloadAsString() + " "));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Fehler: " + ex.getMessage());
		}
	}

	public static void launch(String baseUrl, User username, java.util.List<User> users) {
		RestNetworkClient client = new RestNetworkClient(baseUrl);
		ClientFrame frame = new ClientFrame(client, username, users);
		frame.setVisible(true);
		frame.loadChatHistory();
	}
}