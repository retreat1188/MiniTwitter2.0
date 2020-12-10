package gui;

import javax.swing.JOptionPane;

public class MessageFeed {
	public MessageFeed(String title, String message, int messageType) {
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}
}
