import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class User extends JFrame implements SysEntry {

    String username;
    JFrame frame;
    JTextArea followUserTextArea;
    JTextArea curFollowingTextArea;
    JScrollPane curFollowingScroll;
    JTextArea tweetTextArea;
    JTextArea newsFeedTextArea;
    JScrollPane newsFeedScroll;
    JTextArea alertTextArea;

    public User(String username) {
        this.username = username;
    }

    public void renderGUI() {

        // general components
        Font font = new Font("Arial", Font.BOLD, 20);

        // textArea to write username of person to follow
        JLabel followUserLabel = new JLabel("User name");
        followUserLabel.setBounds(20, 4, 100, 20);
        followUserTextArea = new JTextArea();
        followUserTextArea.setBounds(20, 20, 240, 30);
        followUserTextArea.setFont(font);
        followUserTextArea.setBorder(BorderFactory.createLineBorder(Color.black));

        // button to follow selected user
        JButton followUserButton = new JButton("Follow User");
        followUserButton.setBounds(275, 20, 240, 30);

        // textArea list view of current following
        JLabel curFollowingLabel = new JLabel("Current Following");
        curFollowingLabel.setBounds(20, 75, 240, 20);
        this.curFollowingTextArea = new JTextArea();
        this.curFollowingTextArea.setBounds(20, 95, 495, 200);
        this.curFollowingTextArea.setEditable(false);
        this.curFollowingTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        this.curFollowingScroll = new JScrollPane(curFollowingTextArea);
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
        JButton tweetButton = new JButton("Post tweet");
        tweetButton.setBounds(275, 330, 240, 60);

        // List View to show new feed other user posts
        JLabel newsFeedLabel = new JLabel("News Feed");
        newsFeedLabel.setBounds(20, 415, 100, 20);
        this.newsFeedTextArea = new JTextArea();
        this.newsFeedTextArea.setBounds(20, 440, 495, 200);
        this.newsFeedTextArea.setEditable(false);
        this.newsFeedTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        this.newsFeedScroll = new JScrollPane(newsFeedTextArea);
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
        setTitle(this.username);
        setLayout(null);
        setResizable(false);
        setVisible(true);
    }

}