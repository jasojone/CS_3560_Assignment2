import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class AdminPanel implements ActionListener, TreeSelectionListener {

    private static AdminPanel adminInstance = null;
    JFrame frame = null;
    JTree tree = null;
    JScrollPane treeScroll;
    JTextArea addUserTextArea;
    JTextArea addGroupTextArea;
    JTextArea alertTextArea;
    HashMap<String, User> userMap = new HashMap<String, User>();

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

        this.frame = new JFrame();
        DefaultMutableTreeNode Root = new DefaultMutableTreeNode("Root");

        // general components
        Font font = new Font("Arial", Font.BOLD, 20);

        // tree view component
        JLabel treeLabel = new JLabel("Tree View");
        treeLabel.setBounds(25, 5, 300, 20);
        this.tree = new JTree(Root);
        this.tree.setBounds(25, 25, 350, 500);
        this.tree.setBorder(BorderFactory.createLineBorder(Color.black));
        this.tree.addTreeSelectionListener(this);
        this.treeScroll = new JScrollPane(this.tree);
        this.treeScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.treeScroll.setBounds(25, 25, 350, 500);

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

    private Boolean isAGroupSelected() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        return true;
    }

    private void addUserClicked() {
        // check if user already exists
        String newUserName = addUserTextArea.getText();

        if (this.userMap.containsKey(newUserName)) {
            return;
        }
        this.userMap.put(newUserName, new User(newUserName));

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode newUserNode = new DefaultMutableTreeNode(newUserName);
        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        if (selectedNode == null) {
            root.add(newUserNode);
            treeModel.reload(root);
            System.out.print("New User Node Added");
            return;
        }

        selectedNode.add(newUserNode);
        treeModel.reload(selectedNode);
        this.addUserTextArea.setText("");

    }

    private void addGroupClicked() {

        Object selectedNode = tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode newGroupNode = new DefaultMutableTreeNode(addGroupTextArea.getText());
        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();

        if (selectedNode == null) {
            root.add(newGroupNode);
            treeModel.reload(root);
            System.out.print("New User Node Added");
            return;
        }

        // TreePath path = tree.getSelectionPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectedNode;
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
        int currentIndex = parent.getIndex(node);
        System.out.println(currentIndex);
        treeModel.insertNodeInto(newGroupNode, parent, currentIndex + 1);
        treeModel.reload(root);
        this.addGroupTextArea.setText("");

    }

    private void openUserViewClicked() {

        Object selectedNode = tree.getLastSelectedPathComponent();
        String selectedNodeName = selectedNode.toString();

        userMap.get(selectedNodeName).renderGUI();

    }

    private void showUserTotalClicked() {

    }

    private void showGroupTotalClicked() {

    }

    private void showMessagesTotalClicked() {

    }

    private void showPositivePercClicked() {

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

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        // DefaultMutableTreeNode selectedNode =
        // (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
        // System.out.println(selectedNode);

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