package com.odfin.gui;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogInFrame extends JOptionPane{

	static JTextField txtUserName = new JTextField();
	static JPasswordField passwordField = new JPasswordField();
	
    static Object[] message = {
            "Benutzername:", txtUserName,
            "Passwort:", passwordField
        };
    
	public static String[] showLogInDialog() {
        int option = JOptionPane.showConfirmDialog(null, message,"Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
        
        if (option == JOptionPane.OK_OPTION) {
            String username = txtUserName.getText().trim();
            String password = new String(passwordField.getPassword());
            return new String[] { username, password };
        } else {
            return null;
        }
	}
}
