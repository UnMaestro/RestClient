package com.odfin.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.odfin.core.ClientApp;
import com.odfin.core.ClientStatus;
import com.odfin.domain.User;

public class DMUserPanel extends JPanel{

	
	private JLabel lblPfp = new JLabel();
	private JLabel lblUsername = new JLabel();
	private JLabel lblStatus = new JLabel();
	private JButton btnClose = new JButton("x");
	
	private User panelOwner;
	
	public static final ImageIcon PLACEHOLDERPFP = new ImageIcon(ClientApp.class.getResource("/com/odfin/gui/media/pfpPlaceholder.png"));
	
	public DMUserPanel(User user) {
		panelOwner = user;
		lblUsername.setText(panelOwner.getUsername());
		setLayout(new FlowLayout());
		
		lblPfp.setPreferredSize(new Dimension(50, 50));
		lblPfp.setIcon(resizeIcon(PLACEHOLDERPFP));
		lblPfp.setOpaque(true);

		lblStatus.setBackground(Color.GREEN);
		lblStatus.setOpaque(true);
		lblStatus.setPreferredSize(new Dimension(50, 50));
		
		btnClose.setOpaque(false);
		
		add(lblPfp);
		add(lblUsername);
		add(lblStatus);
		add(btnClose);
	}
	
	public User getUser() {
		return panelOwner;
	}

	public void changeStatus(ClientStatus status) {
		switch (status) {
			case ONLINE -> {
				lblStatus.setBackground(Color.GREEN);
				lblStatus.setToolTipText("Online");
			}
			case OFFLINE -> {
				lblStatus.setBackground(Color.GRAY);
				lblStatus.setToolTipText("Offline");
			}
			case DND -> {
				lblStatus.setBackground(Color.RED);
				lblStatus.setToolTipText("Do-Not-Disturb");
			}
			case AWAY -> {
				lblStatus.setBackground(Color.YELLOW);
				lblStatus.setToolTipText("Abwesend");
			}

			default ->
					throw new IllegalArgumentException("Unexpected value: " + status);
		}
	}
	
    private static ImageIcon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

    	return icon;
    }
}
