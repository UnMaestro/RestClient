package com.odfin.gui;

import com.odfin.domain.Message;
import com.odfin.domain.User;
import com.odfin.network.rest.RestNetworkClient;
import com.odfin.gui.listener.MessageListener;
import com.odfin.gui.listener.StatusListener;
import com.odfin.gui.listener.UserListListener;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClientFrame extends JFrame {
	private final RestNetworkClient client;
	private final String currentUser;
	private final Gson gson = new Gson();

	private JTextArea chatArea;
	private JTextField inputField;
	private JList<User> userList;

	private final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
	private boolean sseRunning = true;

	public ClientFrame(String baseUrl, String currentUser, java.util.List<User> users) {
		this.setTitle("Chat - " + currentUser);
		this.client = new RestNetworkClient(baseUrl);
		this.currentUser = currentUser;
		initComponents(users);
		attachListeners();
		loadChatHistory();
		startSseListener(baseUrl);
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
		addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				sseRunning = false;
				dispose();
				System.exit(0);
			}
		});
		userList.addListSelectionListener(new UserListListener(userList, client));
	}
	private void startSseListener(String baseUrl) {
		Thread sseThread = new Thread(() -> {
			try {
				HttpClient http = HttpClient.newHttpClient();
				HttpRequest req = HttpRequest.newBuilder()
						.uri(URI.create(baseUrl + "/stream"))
						.header("Accept", "text/event-stream")
						.build();
				HttpResponse<java.io.InputStream> resp = http.send(req, HttpResponse.BodyHandlers.ofInputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(resp.body()));
				String line;
				while (sseRunning && (line = reader.readLine()) != null) {
					if (line.startsWith("data: ")) {
						String json = line.substring(6);
						Message m = gson.fromJson(json, Message.class);
						SwingUtilities.invokeLater(() -> appendMessage(m));
					}
				}
			} catch (Exception e) {
				SwingUtilities.invokeLater(() -> System.out.println(e.toString()));
			}
		}, "SSE-Client");
		sseThread.setDaemon(true);
		sseThread.start();
	}

	private void appendMessage(Message m) {
		String time = fmt.format(new Date(m.getCreated()));
		chatArea.append(String.format("[%s] %s: %s\n", time, m.getAuthor(), m.getText()));
	}

	public void refreshChat() {
		try {
			java.util.List<Message> msgs = new ArrayList<>();
			msgs = client.getMessages();
			chatArea.setText("");
			for (Message m : msgs) {
				String time = fmt.format(new Date(m.getCreated()));
				chatArea.append(String.format("[%s] %s: %s\n", time, m.getAuthor(), m.getText()));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Fehler beim Abrufen: " + ex.getMessage());
		}
	}

	public void loadChatHistory() {
		refreshChat();
	}

	public static void launch(String baseUrl, String username, java.util.List<User> users) {
		SwingUtilities.invokeLater(() -> {
			ClientFrame frame = new ClientFrame(baseUrl, username, users);
			frame.setVisible(true);
		});
	}
}
