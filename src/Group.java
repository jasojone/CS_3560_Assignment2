package src;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Visitor.SysEntryVisitor;

public class Group implements SysEntry {

    private UUID ID;
    private String groupName;

    private List<SysEntry> listOfSysEntry;

    public Group(String groupName) {
        this.groupName = groupName;
        this.listOfSysEntry = new ArrayList<SysEntry>();

    }

    public void addUser(User user) {
        this.listOfSysEntry.add(user);
    }

    public String getName() {
        return this.groupName;
    }

    @Override
    public Boolean isGroup() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        return this.ID.toString();
    }

    @Override
    public int accept(SysEntryVisitor visitor) {
        // TODO Auto-generated method stub

        return 0;

    }

    @Override
    public Date getCreationTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setCreationTime(long creationTime) {
        // TODO Auto-generated method stub
        
    }

}
