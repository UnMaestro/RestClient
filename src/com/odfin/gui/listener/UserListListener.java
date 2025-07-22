package com.odfin.gui.listener;

import com.odfin.domain.User;
import com.odfin.network.rest.RestNetworkClient;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class UserListListener implements ListSelectionListener {
    private final JList<User> userList;
    private final RestNetworkClient client;

    public UserListListener(JList<User> userList, RestNetworkClient client) {
        this.userList = userList;
        this.client = client;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            User selected = userList.getSelectedValue();
            if (selected != null) {
                //TODO
            }
        }
    }
}
