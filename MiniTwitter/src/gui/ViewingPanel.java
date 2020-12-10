package gui;

import java.util.Map;
import java.util.Date;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import application.UserInfo;
import application.User;
import application.UserGroup;
import application.Observer;
import application.Profile;
import gui.ControlPanel;

public class ViewingPanel extends ControlPanel {
	private static JFrame frame;
    private GridBagConstraints constraints;

    private JTextField toFollowTextField;

    private JTextArea tweetMessageTextArea;
    private JTextArea currentFollowingTextArea;
    private JTextArea newsFeedTextArea;
    private JTextArea creationTimeArea;

    private JScrollPane tweetMessageScrollPane;
    private JScrollPane currentFollowingScrollPane;
    private JScrollPane newsFeedScrollPane;

    private JButton followUserButton;
    private JButton postTweetButton;

    private Profile user;
    private Map<String, Observer> allUsers;
    private Map<String, JPanel> openPanels;
    
    private long creationTime = System.currentTimeMillis();
    private long lastUpdateTime = System.currentTimeMillis();
    
    public ViewingPanel(Map<String, Observer> allUsers, Map<String, JPanel> allPanels, DefaultMutableTreeNode user) {
        super();

        this.user = (Profile) user;
        this.allUsers = allUsers;
        this.openPanels = allPanels;
        initializeComponents();
        addComponents();
    }


    private void addComponents() {
        addComponent(frame, toFollowTextField, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, followUserButton, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, currentFollowingTextArea, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, tweetMessageScrollPane, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, postTweetButton, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, newsFeedScrollPane, 0, 3, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(frame, creationTimeArea, 2, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    private void initializeComponents() {
        frame = new JFrame("User View");
        formatFrame();

        constraints = new GridBagConstraints();
        constraints.ipady = 100;

        toFollowTextField = new JTextField("User ID");
        followUserButton = new JButton("Follow User");
        initializeFollowUserButtonActionListener();

        currentFollowingTextArea = new JTextArea("Current Following: ");
        formatTextArea(currentFollowingTextArea);
        currentFollowingScrollPane = new JScrollPane(currentFollowingTextArea);
        currentFollowingScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        tweetMessageTextArea = new JTextArea("Tweet Message");
        tweetMessageScrollPane = new JScrollPane(tweetMessageTextArea);
        tweetMessageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        postTweetButton = new JButton("Post Tweet");
        initializePostTweetButtonActionListener();

        newsFeedTextArea = new JTextArea("News Feed: ");
        formatTextArea(newsFeedTextArea);
        newsFeedScrollPane = new JScrollPane(newsFeedTextArea);
        newsFeedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        creationTimeArea = new JTextArea("User Creation Time: " + creationTime);
        

        updateCurrentFollowingTextArea();

        updateNewsFeedTextArea();
    }

    private void formatTextArea(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(8);
        textArea.setEditable(false);
    }

    private void formatFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setTitle(((UserInfo) user).getID());

        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                openPanels.remove(((UserInfo) user).getID());
            }
        });
    }

    private void updateNewsFeedTextArea() {
        String list = "News Feed: \n";
        for (String news : ((User) user).getNewsFeed()) {
            list += " - " + news + ", Last Time:" + lastUpdateTime + "\n";
        }
        
        // show most recent message at top of news feed
        newsFeedTextArea.setText(list);
        newsFeedTextArea.setCaretPosition(0);
    }

    private void updateCurrentFollowingTextArea() {
        String list = "Current Following: \n";
        for (String following : ((User) user).getFollowing().keySet()) {
            list += " - " + following + "\n";
        }
        currentFollowingTextArea.setText(list);
        currentFollowingTextArea.setCaretPosition(0);
    }


    private void initializePostTweetButtonActionListener() {
        postTweetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((User) user).sendMessage(tweetMessageTextArea.getText());

                for (JPanel panel : openPanels.values()) {
                    ((ViewingPanel) panel).updateNewsFeedTextArea();
                }
            }
        });
    }

    private void initializeFollowUserButtonActionListener() {
        followUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserInfo toFollow = (UserInfo) allUsers.get(toFollowTextField.getText());

                if (!allUsers.containsKey(toFollowTextField.getText())) {
                    MessageFeed dialogBox = new MessageFeed("Error!",
                            "User does not exist!",
                            JOptionPane.ERROR_MESSAGE);

                } else if (toFollow.getClass() == UserGroup.class) {
                    MessageFeed dialogBox = new MessageFeed("Error!",
                            "Cannot follow a group!",
                            JOptionPane.ERROR_MESSAGE);
                } else if (allUsers.containsKey(toFollowTextField.getText())) {
                    ((Profile) toFollow).attach((Observer) user);
                }

                // show current following as list
                updateCurrentFollowingTextArea();
            }
        });
    }
}
