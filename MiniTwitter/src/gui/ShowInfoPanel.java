package gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import application.UserInfo;
import visiting.GroupVisitor;
import visiting.Messages;
import visiting.PositiveMessage;
import visiting.UserVisitor;

public class ShowInfoPanel extends ControlPanel {
	private JButton userTotalButton;
    private JButton groupTotalButton;
    private JButton messagesTotalButton;
    private JButton positivePercentageButton;

    private JPanel treePanel;

    public ShowInfoPanel(JPanel treePanel) {
        super();

        this.treePanel = treePanel;
        initializeComponents();
        addComponents();
    }

    private void addComponents() {
        addComponent(this, userTotalButton, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, groupTotalButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, messagesTotalButton, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, positivePercentageButton, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        userTotalButton = new JButton("Show User Total");
        initializeUserTotalButtonActionListener();

        groupTotalButton = new JButton("Show Group Total");
        initializeGroupTotalButtonActionListener();

        messagesTotalButton = new JButton("Show Messages Total");
        initializeMessagesTotalButtonActionListener();

        positivePercentageButton = new JButton("Show Positive Percentage");
        initializePositivePercentageButtonActionListener();
    }

    private DefaultMutableTreeNode getSelectedNode() {
        JTree tree = ((TreePanel) treePanel).getTree();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
        if (!((TreePanel) treePanel).getRoot().equals(selectedNode)) {
            selectedNode = (DefaultMutableTreeNode) selectedNode.getUserObject();
        }

        return selectedNode;
    }

    private void initializeUserTotalButtonActionListener() {
        userTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                UserVisitor visitor = new UserVisitor();
                ((UserInfo) selectedNode).accept(visitor);
                String message = "Total number of individual users within "
                        + ((UserInfo) selectedNode).getID() + ": "
                        + Integer.toString(visitor.readUserInfo(((UserInfo) selectedNode)));

                MessageFeed popUp = new MessageFeed(((UserInfo) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void initializeGroupTotalButtonActionListener() {
        groupTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                GroupVisitor visitor = new GroupVisitor();
                ((UserInfo) selectedNode).accept(visitor);
                String message = "Total number of groups within "
                        + ((UserInfo) selectedNode).getID() + ": "
                        + Integer.toString(visitor.readUserInfo(((UserInfo) selectedNode)));

                MessageFeed popUp = new MessageFeed(((UserInfo) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

    private void initializeMessagesTotalButtonActionListener() {
        messagesTotalButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                Messages visitor = new Messages();
                ((UserInfo) selectedNode).accept(visitor);
                String message = "Total number of messages sent by "
                        + ((UserInfo) selectedNode).getID() + ": "
                        + Integer.toString(visitor.readUserInfo(((UserInfo) selectedNode)));

                MessageFeed popUp = new MessageFeed(((UserInfo) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void initializePositivePercentageButtonActionListener() {
        positivePercentageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get User selected in TreePanel
                DefaultMutableTreeNode selectedNode = getSelectedNode();

                PositiveMessage positiveCountVisitor = new PositiveMessage();
                ((UserInfo) selectedNode).accept(positiveCountVisitor);
                int positiveCount = positiveCountVisitor.readUserInfo(((UserInfo) selectedNode));

                Messages messageCountVisitor = new Messages();
                ((UserInfo) selectedNode).accept(messageCountVisitor);
                int messageCount = messageCountVisitor.readUserInfo(((UserInfo) selectedNode));

                // calculate percentage, set percentage to 0.00 if no messages have yet been sent
                double percentage = 0;
                if (messageCount > 0) {
                    percentage = ((double) positiveCount / messageCount) * 100;
                }
                String percentageString = String.format("%.2f", percentage);

                String message = "Percentage of positive messages sent by "
                        + ((UserInfo) selectedNode).getID() + ": "
                        + percentageString + "%";

                MessageFeed popUp = new MessageFeed(((UserInfo) selectedNode).getID() + " information",
                        message, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
