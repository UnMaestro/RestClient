package com.odfin.gui.listener;

import com.odfin.domain.Message;
import com.odfin.domain.User;
import com.odfin.network.rest.RestNetworkClient;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageListener implements ActionListener {
    private final RestNetworkClient client;
    private final JTextField inputField;
    private final JTextArea chatArea;
    private final User currentUser;

    public MessageListener(RestNetworkClient client, JTextField inputField, JTextArea chatArea, User currentUser) {
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
            client.sendMessage(new Message(currentUser, text, System.currentTimeMillis()));
            inputField.setText("");
            refreshChat();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fehler beim Senden: " + ex.getMessage());
        }
    }

    private void refreshChat() {
        try {
            client.getMessages().forEach(m -> chatArea.append(m.getOwner().getUsername() + ": " + m.getPayloadAsString() + "\n"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Fehler beim Abrufen: " + ex.getMessage());
        }
    }
}