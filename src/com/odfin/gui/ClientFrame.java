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
	private final String currentUser;

	private JTextArea chatArea;
	private JTextField inputField;
	private JList<User> userList;

	public ClientFrame(RestNetworkClient client, String currentUser, java.util.List<User> users) {
		super("Chat - " + currentUser);
		this.client = client;
		this.currentUser = currentUser;
		initComponents(users);
		attachListeners();
	}

	private void initComponents(java.util.List<User> users) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		chatArea = new JTextArea();
		chatArea.setEditable(false);
		add(new JScrollPane(chatArea), BorderLayout.CENTER);

		inputField = new JTextField();
		add(inputField, BorderLayout.SOUTH);

		userList = new JList<>(new DefaultListModel<>());
		DefaultListModel<User> model = (DefaultListModel<User>) userList.getModel();
		users.forEach(model::addElement);
		add(new JScrollPane(userList), BorderLayout.WEST);

		setSize(600, 400);
		setLocationRelativeTo(null);
	}

	private void attachListeners() {
		inputField.addActionListener(new MessageListener(client, inputField, chatArea, currentUser));
		addWindowListener(new StatusListener(client, new User(currentUser)));
		userList.addListSelectionListener(new UserListListener(userList, client));
	}

	public void loadChatHistory() { new MessageListener(client, inputField, chatArea, currentUser).refreshChat(); }

	public static void launch(String baseUrl, String username, java.util.List<User> users) {
		RestNetworkClient client = new RestNetworkClient(baseUrl);
		ClientFrame frame = new ClientFrame(client, username, users);
		frame.setVisible(true);
		frame.loadChatHistory();
	}
}
