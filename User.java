import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class User extends JFrame implements SysEntry {

    JFrame frame;
    String username = "";

    public User(String username) {

        // general components
        Font font = new Font("Arial", Font.BOLD, 20);

        // textArea to show user name
        JTextArea showNameTextArea = new JTextArea(username);
        showNameTextArea.setEditable(false);
        showNameTextArea.setBounds(20, 20, 240, 30);
        showNameTextArea.setFont(font);

        // button to follow selected user
        JButton followUserButton = new JButton("Follow User");
        followUserButton.setBounds(275, 20, 240, 30);

        // Listview of current following

        // textArea to enter tweet message

        // button to post tweet message

        // List View to show new feed/ other user posts

        add(showNameTextArea);
        add(followUserButton);

        setSize(550, 550);
        setLayout(null);
        setResizable(false);
        setVisible(true);

    }

}