package Visitor;

import src.Group;
import src.User;

public interface SysEntryVisitor {

    public void visit(User user);

    public void visit(Group group);

}
