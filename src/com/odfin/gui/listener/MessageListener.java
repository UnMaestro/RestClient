package com.odfin.gui.listener;

import com.odfin.domain.Message;
import com.odfin.network.rest.RestNetworkClient;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageListener implements ActionListener {
    private final RestNetworkClient client;
    private final JTextField inputField;
    private final JTextArea chatArea;
    private final String currentUser;
    private final SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");


    public MessageListener(RestNetworkClient client, JTextField inputField, JTextArea chatArea, String currentUser) {
        this.client = client;
        this.inputField = inputField;
        this.chatArea = chatArea;
        this.currentUser = currentUser;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;
        try {
            client.sendMessage(new Message(currentUser, text));
            inputField.setText("");
            refreshChat();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fehler beim Senden: " + ex.getMessage());
        }
    }

    public void refreshChat() {
        try {
            chatArea.setText("");
            for (Message m : client.getMessages()) {
                String time = fmt.format(new Date(m.getCreated()));
                chatArea.append(String.format("[%s] %s: %s\n", time, m.getAuthor(), m.getText()));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fehler beim Abrufen: " + ex.getMessage());
        }
    }
}