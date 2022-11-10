package Visitor;

import src.User;

public class UserRenderVisitorImpl implements SysEntryVisitor {

    @Override
    public int visit(User user) {
        // TODO Auto-generated method stub
        user.renderGUI();
        return 0;
    }

}
