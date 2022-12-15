package src;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import Visitor.LastUpdatedVisitorImpl;
import Visitor.PositiveCountVisitorImpl;
import Visitor.SysEntryVisitor;
import Visitor.TweetCountVisitorImpl;
import Visitor.UserCountVisitorImpl;
import Visitor.UserRenderVisitorImpl;

public class AdminPanel implements ActionListener {

    private static AdminPanel adminInstance = null;

    private int numberOfGroups = 1;

    private JFrame frame = null;
    private JTree tree = null;
    private JScrollPane treeScroll;
    private JTextArea addUserTextArea;
    private JTextArea addGroupTextArea;
    private JTextArea alertTextArea;
    private HashMap<String, User> userMap;
    private HashMap<String, Group> groupMap;
    // User/Group ID verification. One more button should be added to the main Admin UI to
    // validate if all the IDs used in the users and groups are valid, based on the following two
    // criteria: 1) all the IDs must be unique - there should not be duplicated IDs; 2) all the IDs
    // should not contain spaces. You need to show a dialog (or print in console if GUI is not
    // available) to tell whether all the IDs are valid or not. Note: you only need to output the
    // validation result. You do NOT need to fix or prevent the invalid inputs.
    
    

    private AdminPanel() {
    }

    public static AdminPanel getInstance() {

        if (adminInstance == null) {
            adminInstance = new AdminPanel();
        }

        return adminInstance;

    }

    public void renderGUI() {

        if (this.frame != null || this.tree != null) {
            return;
        }

        this.userMap = new HashMap<>();
        this.groupMap = new HashMap<>();

        this.frame = new JFrame();
        Group rootGroup = new Group("Root");
        DefaultMutableTreeNode Root = new DefaultMutableTreeNode(new TreeNode(rootGroup));

        // general components
        Font font = new Font("Arial", Font.BOLD, 20);

        // tree view component
        JLabel treeLabel = new JLabel("Tree View");
        treeLabel.setBounds(25, 5, 300, 20);
        this.tree = new JTree(Root);
        this.tree.setBounds(25, 25, 350, 500);
        this.tree.setBorder(BorderFactory.createLineBorder(Color.black));
        this.treeScroll = new JScrollPane(this.tree);
        this.treeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.treeScroll.setBounds(25, 25, 350, 500);

        // custom icons for tree
        tree.setCellRenderer(new DefaultTreeCellRenderer() {

            private Icon groupIcon = UIManager.getIcon("FileView.directoryIcon");
            private Icon userIcon = UIManager.getIcon("FileView.fileIcon");

            @Override
            public Component getTreeCellRendererComponent(JTree tree,
                    Object value, boolean selected, boolean expanded,
                    boolean isLeaf, int row, boolean focused) {
                Component c = super.getTreeCellRendererComponent(tree, value,
                        selected, expanded, isLeaf, row, focused);

                DefaultMutableTreeNode nodeValue = (DefaultMutableTreeNode) value;
                TreeNode userObject = (TreeNode) nodeValue.getUserObject();

                if (userObject.isGroup)
                    setIcon(groupIcon);
                else
                    setIcon(userIcon);
                return c;
            }

        });

        // textArea for add User
        JLabel addUserLabel = new JLabel("New User Name");
        addUserLabel.setBounds(390, 5, 180, 20);
        addUserTextArea = new JTextArea();
        addUserTextArea.setBounds(390, 25, 180, 30);
        addUserTextArea.setFont(font);
        addUserTextArea.setBorder(BorderFactory.createLineBorder(Color.black));

        // button for add User
        JButton addUserButton = new JButton("Add User");
        addUserButton.setBounds(595, 25, 180, 30);
        addUserButton.addActionListener(this);

        // textArea for add Group
        JLabel addGroupLabel = new JLabel("New Group Name");
        addGroupLabel.setBounds(390, 75, 180, 20);
        addGroupTextArea = new JTextArea();
        addGroupTextArea.setBounds(390, 95, 180, 30);
        addGroupTextArea.setFont(font);
        addGroupTextArea.setBorder(BorderFactory.createLineBorder(Color.black));

        // button for add Group
        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.setBounds(595, 95, 180, 30);
        addGroupButton.addActionListener(this);

        // button to open user view
        JButton openUserViewButton = new JButton("Open User View");
        openUserViewButton.setBounds(390, 165, 385, 50);
        openUserViewButton.addActionListener(this);

        // button to show user total
        JButton showUserTotalButton = new JButton("Show User Total");
        showUserTotalButton.setBounds(390, 405, 180, 50);
        showUserTotalButton.addActionListener(this);

        // button to show group total
        JButton showGroupTotalButton = new JButton("Show Group Total");
        showGroupTotalButton.setBounds(595, 405, 180, 50);
        showGroupTotalButton.addActionListener(this);

        // button to show messages total
        JButton showMessagesTotalButton = new JButton("Show Messages Total");
        showMessagesTotalButton.setBounds(390, 475, 180, 50);
        showMessagesTotalButton.addActionListener(this);

        // button to show positive percentage
        JButton showPositivePercButton = new JButton("Show Positive Percentage");
        showPositivePercButton.setBounds(595, 475, 180, 50);
        showPositivePercButton.addActionListener(this);

        // button to validate IDs
        JButton validateButton = new JButton("Validate IDs");
        validateButton.setBounds(390, 335, 385, 50);
        validateButton.addActionListener(this);

        //button to find last update
        JButton lastUpdateButton = new JButton("Find Last Update");
        lastUpdateButton.setBounds(390, 265, 385, 50);
        lastUpdateButton.addActionListener(this);


        frame.add(this.treeScroll);
        frame.add(treeLabel);
        frame.add(addUserLabel);
        frame.add(addUserTextArea);
        frame.add(addUserButton);
        frame.add(addGroupLabel);
        frame.add(addGroupTextArea);
        frame.add(addGroupButton);
        frame.add(openUserViewButton);
        frame.add(showUserTotalButton);
        frame.add(showGroupTotalButton);
        frame.add(showMessagesTotalButton);
        frame.add(showPositivePercButton);
        frame.add(validateButton);
        frame.add(lastUpdateButton);

        frame.setTitle("Admin Panel");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    private void addUserClicked() {
        // check if user already exists
        String newUserName = addUserTextArea.getText();
        if (this.userMap.containsKey(newUserName)) {
            return;
        }

        if (newUserName.length() == 0) {
            return;
        }

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        User newUser = new User(newUserName);
        DefaultMutableTreeNode newUserNode = new DefaultMutableTreeNode(new TreeNode(newUser));

        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        if (selectedNode == null) {
            root.add(newUserNode);
            treeModel.reload(root);
            this.userMap.put(newUserName, newUser);
            this.addUserTextArea.setText(null);
            System.out.println("New User Node Added");
            return;
        }

        TreeNode selectedTreeNode = (TreeNode) selectedNode.getUserObject();
        DefaultMutableTreeNode parentOfSelectedNode = (DefaultMutableTreeNode) selectedNode.getParent();

        if (selectedTreeNode.isGroup) {
            Group selectedGroupTreeNode = (Group) selectedTreeNode.entry;
            SysEntryVisitor visitor = new UserRenderVisitorImpl();
            selectedGroupTreeNode.accept(visitor);
            selectedNode.add(newUserNode);
        } else {
            parentOfSelectedNode.add(newUserNode);
        }

        if (parentOfSelectedNode == null) {
            treeModel.reload(root);
        } else {
            treeModel.reload(parentOfSelectedNode);
        }

        TreePath path = tree.getSelectionPath();
        tree.expandPath(path);
        this.userMap.put(newUserName, newUser);
        this.addUserTextArea.setText(null);

        System.out.println("USER MAP");
        for (User element : this.userMap.values()) {
            System.out.println(element.getName());
        }

    }

    private void addGroupClicked() {

        // check if group already exists
        String newGroupName = addGroupTextArea.getText();
        if (this.groupMap.containsKey(newGroupName)) {
            return;
        }

        if (newGroupName.length() == 0) {
            return;
        }

        this.numberOfGroups += 1;

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        Group newGroup = new Group(newGroupName);
        DefaultMutableTreeNode newGroupNode = new DefaultMutableTreeNode(new TreeNode(newGroup));

        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        if (selectedNode == null) {
            root.add(newGroupNode);
            treeModel.reload(root);
            this.groupMap.put(newGroupName, newGroup);
            this.addGroupTextArea.setText(null);
            System.out.println("New Group Node Added");
            return;
        }

        TreeNode selectedTreeNode = (TreeNode) selectedNode.getUserObject();
        DefaultMutableTreeNode parentOfSelectedNode = (DefaultMutableTreeNode) selectedNode.getParent();

        if (selectedTreeNode.isGroup) {
            selectedNode.add(newGroupNode);
        } else {

            // TreePath path = tree.getSelectionPath();
            int currentIndex = parentOfSelectedNode.getIndex(selectedNode);
            treeModel.insertNodeInto(newGroupNode, parentOfSelectedNode, currentIndex + 1);

        }

        if (parentOfSelectedNode == null) {
            treeModel.reload(root);
        } else {
            treeModel.reload(parentOfSelectedNode);
        }

        TreePath path = tree.getSelectionPath();
        tree.expandPath(path);

        this.groupMap.put(newGroupName, newGroup);
        this.addGroupTextArea.setText(null);

    }

    private void openUserViewClicked() {

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        TreeNode selectedNodeTreeNode = (TreeNode) selectedNode.getUserObject();

        SysEntryVisitor visitor = new UserRenderVisitorImpl();

        User selectedUser = (User) selectedNodeTreeNode.entry;
        selectedUser.accept(visitor);
    }

    public User getUser(String username) {
        for (User element : userMap.values()) {
            System.out.println(element);
        }

        if (!this.userMap.containsKey(username)) {
            return null;
        }

        return this.userMap.get(username);
    }

    public int getNumOfGroups() {
        return this.numberOfGroups;
    }

    // get total number of users
    private void showUserTotalClicked() {
        int numberOfUsers = 0;
        SysEntryVisitor userCountVisitor = new UserCountVisitorImpl();
        for (User user : this.userMap.values()) {
            numberOfUsers += user.accept(userCountVisitor);
        }

        JOptionPane.showMessageDialog(frame, "Total number of users: " + numberOfUsers);

    }

    private void showGroupTotalClicked() {

        JOptionPane.showMessageDialog(frame, numberOfGroups);

    }

    private void showMessagesTotalClicked() {

        int numberOfMessages = 0;
        SysEntryVisitor tweetCountVisitor = new TweetCountVisitorImpl();
        for (User user : this.userMap.values()) {
            numberOfMessages += user.accept(tweetCountVisitor);
        }

        JOptionPane.showMessageDialog(frame, "Total number of messages: " + numberOfMessages);

    }

    private void showPositivePercClicked() {
        int numberOfPositive = 0;
        int numberOfMessages = 0;
        SysEntryVisitor numberOfPosVisitor = new PositiveCountVisitorImpl();
        SysEntryVisitor numberOfMesVisitor = new TweetCountVisitorImpl();
        for (User user : this.userMap.values()) {
            numberOfPositive += user.accept(numberOfPosVisitor);
            numberOfMessages += user.accept(numberOfMesVisitor);
        }

        System.out.println(numberOfPositive);
        System.out.println(numberOfMessages);

        if (numberOfMessages == 0) {
            return;
        }

        Double positivePerc = (numberOfPositive * 1.0 / numberOfMessages) * 100.0;

        JOptionPane.showMessageDialog(frame, "Percentage of positive: " + positivePerc + "%");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String action = e.getActionCommand();

        switch (action) {

            case "Add User":
                addUserClicked();
                break;
            case "Add Group":
                addGroupClicked();
                break;
            case "Open User View":
                openUserViewClicked();
                break;
            case "Show User Total":
                showUserTotalClicked();
                break;
            case "Show Group Total":
                showGroupTotalClicked();
                break;
            case "Show Messages Total":
                showMessagesTotalClicked();
                break;
            case "Show Positive Percentage":
                showPositivePercClicked();
                break;
            case "Validate IDs":
                validateIds();
                break;
            case "Find Last Update":
                lastUpdated();
                break;

            default:
                System.out.println("Something went wrong!");
                break;

        }

    }
    // last updated
    // get the user name and time stamp of the last updated user
    public void lastUpdated(){
        String lastUpdatedUser = null;
        long lastUpdatedTime = 0;

        for(User user : userMap.values()){
            if(user.lastUpdateTime > lastUpdatedTime){
                lastUpdatedUser = user.getName();
                lastUpdatedTime = user.lastUpdateTime;
            }
        }

        if(lastUpdatedUser == null){
            JOptionPane.showMessageDialog(frame, "No user has been updated yet!");
            return;
        }

        JOptionPane.showMessageDialog(frame, "Last updated user: " + lastUpdatedUser + " at " + Long.toString(lastUpdatedTime));
    }
    // public void lastUpdated() {
            
    //         String lastUpdatedUser = null;
    //         long lastUpdatedTime = 0;
    
    //         for (User user : userMap.values()) {
    //             if (user.lastUpdateTime > lastUpdatedTime) {
    //                 lastUpdatedUser = user.getName();

    //                 lastUpdatedTime = (long) user.getLastUpdated();
    //             }
    //         }
    
    //         if (lastUpdatedUser == null) {
    //             JOptionPane.showMessageDialog(frame, "No user has been updated yet!");
    //             return;
    //         }
    
    //         JOptionPane.showMessageDialog(frame, "Last updated user: " + lastUpdatedUser + " at " + lastUpdatedTime);
    
    // }

    public void addTestValues() {

        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("John");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Tomato");
        DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("Potato");
        DefaultMutableTreeNode node4 = new DefaultMutableTreeNode("David");
        root.add(node1);
        root.add(node2);
        root.add(node3);
        root.add(node4);

        treeModel.reload(root);

    }
    // validate IDs of all users in the system are unique and not null

    public void validateIds() {
        if (validateIdsWorker()) {
            JOptionPane.showMessageDialog(frame, "All IDs are valid");
        } else {
            JOptionPane.showMessageDialog(frame, "There are invalid IDs");
        }
    }
    public boolean validateIdsWorker(){
        for (User user : this.userMap.values()) {
            if(user.getID() == null){
                return false;
            }
        }
        return true;
    }

    // find the last updated user in the system and display their name and time
    
    
}