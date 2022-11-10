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

            default:
                System.out.println("Something went wrong!");
                break;

        }

    }

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

}