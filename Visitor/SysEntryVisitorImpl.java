package Visitor;

import src.Group;
import src.User;

public class SysEntryVisitorImpl implements SysEntryVisitor {

    @Override
    public void visit(User user) {
        // TODO Auto-generated method stub
        user.renderGUI();
    }

    @Override
    public void visit(Group group) {
        // TODO Auto-generated method stub

    }

}
