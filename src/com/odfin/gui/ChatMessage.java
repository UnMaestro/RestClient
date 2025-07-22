package com.odfin.gui;

import java.awt.Color;
import java.sql.Date;
import java.sql.Timestamp;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.odfin.domain.Message;
import com.odfin.domain.User;

public class ChatMessage extends JPanel{

	private JLabel lblUserPfp = new JLabel();
	private JLabel lblUsername = new JLabel();
	private JLabel lblTextMessage = new JLabel();
	
	private final Color CLIENT_COLOR = Color.blue;
	private final Color OTHER_COLORS = Color.CYAN;
	
	private Message messageContent;
	
	public ChatMessage(Message msg, boolean isClient) {

		messageContent = msg;
		setBackground(isClient ? CLIENT_COLOR : OTHER_COLORS);
		
		setOpaque(true);
		lblTextMessage.setText(messageContent.getText());
		
		lblUsername.setOpaque(true);
		lblTextMessage.setOpaque(true);
		
		Date date = new Date(messageContent.getCreated());
		Timestamp timestamp = new Timestamp(date.getTime());
		lblUsername.setText(messageContent.getAuthor() + " " + timestamp);
		setLayout(new BoxLayout(this, 3));
		
		add(lblUsername);
		add(lblTextMessage);
	}
	
	public String getMessage() {
		return messageContent.getText();
	}
	
	public String getUser() {
		return messageContent.getAuthor();
	}
	
	public void setPanelBackground(Color color) {
//		setBackground(color);
	}
	
}
