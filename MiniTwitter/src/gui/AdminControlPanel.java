package gui;

// Class for main UI, applied as Singleton design pattern

import application.UserGroup;
import application.Observer;
import application.UserInfo;

import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.HashMap;

public class AdminControlPanel extends ControlPanel {
	private static AdminControlPanel Link; 
	
	private static JFrame frame;
	private JPanel treePanel;
	private JPanel addUserPanel;
	private JPanel openUserPanel;
	private JPanel showInfoPanel;
	
	private DefaultMutableTreeNode root;
	private Map<String, Observer> allUsers;
	
	public static AdminControlPanel getInstance() {
		if (Link == null) {
			synchronized (Driver.class) {
				if (Link == null) {
					Link = new AdminControlPanel();
				}
			}
		}
		
		return Link;
	}
	
	private AdminControlPanel() {
		super();
		initializeComponents();
		addComponents();
	}
	
	private void addComponents() {
		addComponent(frame, treePanel, 0, 0, 1, 6, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(frame, addUserPanel, 1, 0, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(frame, openUserPanel, 1, 2, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		addComponent(frame, showInfoPanel, 1, 4, 2, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
	}
	
	private void initializeComponents() {
		frame = new JFrame("Welcome to the MiniTwitter App!");
		formatFrame();
		
		allUsers = new HashMap<String, Observer>();
		root = new UserGroup("Root");
		allUsers.put(((UserInfo) root).getID(), (Observer) this.root);
		
		treePanel = new TreePanel(root);
		addUserPanel = new UserPanel(treePanel, allUsers);
		openUserPanel = new OpenUserViewPanel(treePanel, allUsers);
		showInfoPanel = new ShowInfoPanel(treePanel);
		
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		InputMap im = (InputMap) UIManager.get("Button.focusInputMap");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
	}
	
	private void formatFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.setSize(1000, 500);
		frame.setVisible(true);
	}
}
