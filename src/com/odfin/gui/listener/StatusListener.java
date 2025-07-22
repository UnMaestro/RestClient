package com.odfin.gui.listener;

import com.odfin.domain.Message;
import com.odfin.domain.Status;
import com.odfin.domain.User;
import com.odfin.network.rest.RestNetworkClient;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StatusListener extends WindowAdapter {
    private final RestNetworkClient client; // falls Status-Updates via REST benötigt
    private final User currentUser;

    public StatusListener(RestNetworkClient client, User currentUser) {
        this.client = client;
        this.currentUser = currentUser;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            client.sendMessage(new Message(currentUser, Status.OFFLINE.name(), System.currentTimeMillis()));
        } catch (Exception ex) {

        }
        System.exit(0);
    }
}