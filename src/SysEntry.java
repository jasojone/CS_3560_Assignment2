package src;

import java.sql.Date;
import java.util.UUID;

import Visitor.SysEntryVisitor;

public interface SysEntry {

    public int accept(SysEntryVisitor visitor);

    public String getName();

    public Boolean isGroup();

    public String getID();

//     2. Add creation time attribute to User and Group. Both User and Group should include a
// new attribute - creationTime. The type should be long. And its value should be given
// whenever the object is created. You can call System.currenttimemillis() to get the
// current system timestamp. When you open the User View, this time value should be
// display (or printed out) somewhere in the UI (or console).

    public Date getCreationTime();

    public void setCreationTime(long creationTime);

    


}
