import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class AdminPanel {

    private static AdminPanel adminInstance = null;
    JFrame frame;
    JTree tree;

    private AdminPanel() {
    }

    public static AdminPanel getInstance() {

        if (adminInstance == null) {
            adminInstance = new AdminPanel();
        }

        return adminInstance;

    }

    public void renderGUI() {

        this.frame = new JFrame();

        JLabel treeLabel = new JLabel("Tree View");
        treeLabel.setBounds(25, 0, 300, 20);

        DefaultMutableTreeNode Root = new DefaultMutableTreeNode("Root");

        this.tree = new JTree(Root);
        this.tree.setBounds(25, 25, 350, 500);
        this.tree.setBorder(BorderFactory.createLineBorder(Color.black));

        frame.add(tree);
        frame.add(treeLabel);

        frame.setTitle("Admin Panel");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }

}