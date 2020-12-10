package gui;

import java.util.Map;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import application.UserInfo;
import application.User;
import application.UserGroup;
import application.Observer;

public class UserPanel extends ControlPanel {
	private JPanel treePanel;
    private Map<String, Observer> allUsers;

    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton addIDVerificationButton;
    private JButton addFindLastUpdatedUserButton;
    private JTextField userId;
    private JTextField groupId;
    
    private String lastUpdatedUser;

    public UserPanel(JPanel treePanel, Map<String, Observer> allUsers) {
        super();
        this.treePanel = treePanel;
        this.allUsers = allUsers;

        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, userId, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupId, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addGroupButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addIDVerificationButton, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, addFindLastUpdatedUserButton, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        userId = new JTextField("User ID");
        groupId = new JTextField("Group ID");

        addUserButton = new JButton("Add User");
        initializeUserButtonActionListener();

        addGroupButton = new JButton("Add Group");
        initializeGroupButtonActionListener();
        
        addIDVerificationButton = new JButton("Verify ID");
        initializeIDButtonActionListener();
        
        addFindLastUpdatedUserButton = new JButton("Find Last User");
        initializeLastUpdatedButtonActionListener();
    }

    private void initializeUserButtonActionListener() {
        addUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // check if user ID already exists
                if (allUsers.containsKey(userId.getText())) {
                    MessageFeed dialogBox = new MessageFeed("Error!",
                            "User already exists!\nPlease choose a different user name.",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Observer child = new User(userId.getText());

                    allUsers.put(((User) child).getID(), child);
                    ((TreePanel) treePanel).addSingleUser((DefaultMutableTreeNode) child);
                }
            }
        });
    }

    private void initializeGroupButtonActionListener() {
        addGroupButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // check if group ID already exists
                if (allUsers.containsKey(groupId.getText())) {
                    MessageFeed dialogBox = new MessageFeed("Error!",
                            "Group already exists!\nPlease choose a different group name.",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Observer child = new UserGroup(groupId.getText());

                    allUsers.put(((User) child).getID(), child);
                    ((TreePanel) treePanel).addGroupUser((DefaultMutableTreeNode) child);
                }
            }
        });
    }
    
    private void initializeIDButtonActionListener() {
    	addIDVerificationButton.addActionListener(new ActionListener() {
    		
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// check for user/group id validation
    			if (allUsers.containsKey(groupId.getText())){
    				MessageFeed dialogBox = new MessageFeed("Notice!",
                     "All User/Group ID is valid.", JOptionPane.PLAIN_MESSAGE);
    			} else {
    				 Observer child = new UserGroup(groupId.getText());

                     allUsers.put(((User) child).getID(), child);
                     ((TreePanel) treePanel).addGroupUser((DefaultMutableTreeNode) child);
    			}
    	};
    });
    }
    
    private void initializeLastUpdatedButtonActionListener() {
    	addFindLastUpdatedUserButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			lastUpdatedUser = allUsers.toString();
    			long largestTime = 0;
    			if (allUsers.containsKey(groupId.getText())) {
    				MessageFeed dialogBox = new MessageFeed("Notice!", "Latest known user is" + lastUpdatedUser, JOptionPane.PLAIN_MESSAGE);
    			}
    				else {
       				 Observer child = new UserGroup(groupId.getText());

                        allUsers.put(((User) child).getID(), child);
                        ((TreePanel) treePanel).addGroupUser((DefaultMutableTreeNode) child);
    			}
    		}
    	});
    }
}
