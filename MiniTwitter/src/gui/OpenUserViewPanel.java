package gui;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import application.UserInfo;
import application.User;
import application.UserGroup;
import application.Observer;
import gui.ControlPanel;
import gui.TreePanel;

public class OpenUserViewPanel extends ControlPanel {
	private JButton openUserViewButton;
    private JPanel spacerPanel;
    private JPanel contentPane;
    private JTextField txtCreationTime;
    private UserGroup root;

    private JPanel treePanel;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;

    public OpenUserViewPanel(JPanel treePanel, Map<String, Observer> allUsers) {
        super();

        this.treePanel = treePanel;
        this.allUsers = allUsers;
        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, openUserViewButton, 1, 2, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, spacerPanel, 1, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        openPanels = new HashMap<String, JPanel>();

        openUserViewButton = new JButton("Open User View");
        initializeOpenUserViewActionListener();

        // Empty spacer
        spacerPanel = new JPanel();
    }

    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreePanel) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        if (!((TreePanel) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }

    private void initializeOpenUserViewActionListener() {
        openUserViewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                if (!allUsers.containsKey(((UserInfo) selectedNode).getID())) {
                    MessageFeed dialogBox = new MessageFeed("Error!",
                            "No user exists!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == UserGroup.class) {
                    MessageFeed dialogBox = new MessageFeed("Error!",
                            "Cannot open user view for a group!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (openPanels.containsKey(((UserInfo) selectedNode).getID())) {
                    MessageFeed dialogBox = new MessageFeed("Error!",
                            "User view already open for " + ((UserInfo) selectedNode).getID() + "!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (selectedNode.getClass() == User.class) {
                    ViewingPanel userView = new ViewingPanel(allUsers, openPanels, selectedNode);
                    openPanels.put(((UserInfo) selectedNode).getID(), userView);
                }
            }
        });
    }
}
