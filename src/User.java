package src;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import Observer.Observer;
import Visitor.SysEntryVisitor;

public class User extends JFrame implements ActionListener, SysEntry, Observer, Subject {

    private UUID ID;
    private String userName;

    // overall tweets
    private List<Tweet> allTweets = new ArrayList<Tweet>();
    // this users tweet list
    private List<Tweet> myTweets = new ArrayList<Tweet>();

    // observers -> users that are following me
    private List<User> observers = new ArrayList<User>();

    private List<User> usersIAmFollowing = new ArrayList<User>();

    JFrame frame;
    JTextArea followUserTextArea;
    JTextArea tweetTextArea;
    JScrollPane curFollowingScroll;

    JList<User> curFollowingList;
    JList<Tweet> newsFeedList;
    DefaultListModel<User> curFollowingListModel;
    DefaultListModel<Tweet> newsFeedListModel;

    JScrollPane newsFeedScroll;
    JTextArea alertTextArea;
    private Date creationTime;

    public User(String username) {
        this.userName = username;
        this.ID = UUID.randomUUID();
    }

    public void followUser(User user) {

        this.usersIAmFollowing.add(user);

    }

    public void addFollower(User user) {

    }

    public void renderGUI() {

        // general components
        Font font = new Font("Arial", Font.BOLD, 20);
        Font listFont = new Font("Arial", Font.BOLD, 25);

        // textArea to write username of person to follow
        JLabel followUserLabel = new JLabel("User name");
        followUserLabel.setBounds(20, 4, 100, 20);
        this.followUserTextArea = new JTextArea();
        this.followUserTextArea.setBounds(20, 20, 240, 30);
        this.followUserTextArea.setFont(font);
        this.followUserTextArea.setBorder(BorderFactory.createLineBorder(Color.black));

        // button to follow selected user
        JButton followUserButton = new JButton("Follow User");
        followUserButton.setBounds(275, 20, 240, 30);
        followUserButton.addActionListener(this);

        // list view of current following
        JLabel curFollowingLabel = new JLabel("Current Following");
        curFollowingLabel.setBounds(20, 75, 240, 20);
        this.curFollowingListModel = new DefaultListModel<>();
        this.curFollowingList = new JList<>(curFollowingListModel);
        this.curFollowingList.setFont(listFont);
        this.curFollowingList.setBounds(20, 95, 495, 200);
        this.curFollowingList.setBorder(BorderFactory.createLineBorder(Color.black));
        this.curFollowingScroll = new JScrollPane(curFollowingList);
        this.curFollowingScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.curFollowingScroll.setBounds(20, 95, 495, 200);

        // textArea to enter tweet message
        JLabel tweetLabel = new JLabel("Tweet Message");
        tweetLabel.setBounds(20, 310, 495, 20);
        this.tweetTextArea = new JTextArea();
        this.tweetTextArea.setBounds(20, 330, 240, 60);
        this.tweetTextArea.setFont(font);
        this.tweetTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        this.tweetTextArea.setLineWrap(true);

        // button to post tweet message
        JButton tweetButton = new JButton("Post Tweet");
        tweetButton.setBounds(275, 330, 240, 60);
        tweetButton.addActionListener(this);

        // List View to show new feed other user posts
        JLabel newsFeedLabel = new JLabel("News Feed");
        newsFeedLabel.setBounds(20, 415, 100, 20);
        this.newsFeedListModel = new DefaultListModel<>();
        this.newsFeedList = new JList<>(newsFeedListModel);
        this.newsFeedList.setFont(listFont);
        this.newsFeedList.setBounds(20, 440, 495, 200);
        this.newsFeedList.setBorder(BorderFactory.createLineBorder(Color.black));
        this.newsFeedScroll = new JScrollPane(newsFeedList);
        this.newsFeedScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.newsFeedScroll.setBounds(20, 440, 495, 200);

        add(followUserLabel);
        add(followUserTextArea);
        add(followUserButton);
        add(curFollowingLabel);
        add(curFollowingScroll);
        add(tweetLabel);
        add(tweetTextArea);
        add(tweetButton);
        add(newsFeedLabel);
        add(newsFeedScroll);

        setSize(550, 700);
        setTitle(this.userName);
        setLayout(null);
        setResizable(false);
        setVisible(true);
    }

    public void addObserver(User user) {

        this.observers.add(user);

    }

    public int getNumberOfTweets() {
        return this.myTweets.size();
    }

    public List<Tweet> getTweets() {
        return this.myTweets;
    }

    @Override
    public Boolean isGroup() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        return this.ID.toString();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return this.userName;
    }

    @Override
    public int accept(SysEntryVisitor visitor) {
        // TODO Auto-generated method stub
        return visitor.visit(this);
    }

    @Override
    public void Notify(Tweet newTweet) {
        // TODO Auto-generated method stub

        for (User observer : observers) {
            observer.update(newTweet);

        }

    }

    @Override
    public void update(Tweet newTweet) {
        // TODO Auto-generated method stub
        this.allTweets.add(newTweet);
        this.newsFeedListModel.addElement(newTweet);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String action = e.getActionCommand();
        System.out.println("Button press");
        System.out.println(action);

        switch (action) {
            case "Follow User":
                followUserClicked();
                break;
            case "Post Tweet":
                postTweetClicked();
                break;
            default:
                System.out.println("Error in User class events!");

        }

    }

    private void followUserClicked() {
        // check if the user exists
        String userToFollowName = this.followUserTextArea.getText();
        AdminPanel tempAdmin = AdminPanel.getInstance();
        User userToFollow = tempAdmin.getUser(userToFollowName);
        if (userToFollow == null) {
            return;
        }

        // check if already following
        if (this.curFollowingListModel.contains(userToFollow)) {
            return;
        }

        userToFollow.addObserver(this);
        this.curFollowingListModel.addElement(userToFollow);

        System.out.println(userToFollowName + " has been followed!");
    }

    private void postTweetClicked() {
        Tweet newTweet = new Tweet(this.tweetTextArea.getText(), this.getName());
        this.myTweets.add(newTweet);
        this.allTweets.add(newTweet);

        this.newsFeedListModel.addElement(newTweet);

        // notify all observers
        this.Notify(newTweet);

    }

    public String toString() {
        return this.userName;
    }

    public Object getLastUpdated() {
        // get the last updated tweet
        if (this.allTweets.size() > 0) {
            return this.allTweets.get(this.allTweets.size() - 1);
        }

        return null;
    }
    // set the creation time of the user
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    @Override
    public void setCreationTime(long creationTime) {
        // TODO Auto-generated method stub
        
    }


}