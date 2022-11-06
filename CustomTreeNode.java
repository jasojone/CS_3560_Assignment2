import java.util.Enumeration;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class CustomTreeNode extends DefaultTreeModel {

    Boolean isGroup = false;

    CustomTreeNode(CustomTreeNode rootNode) {
        super((TreeNode) rootNode);

    }

    @Override
    public boolean isLeaf(Object node) {
        // TODO Auto-generated method stub
        if (this.isGroup) {
            return false;
        }
        return true;
    }

}
