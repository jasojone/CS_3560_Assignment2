package src;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeNode {

    public Boolean isGroup;
    public SysEntry entry;

    public TreeNode(SysEntry newEntry) {
        this.entry = newEntry;
        this.isGroup = newEntry.isGroup();
    }

    @Override
    public String toString() {
        return entry.getName();

    }
}
