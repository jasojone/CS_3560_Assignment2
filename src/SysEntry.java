package src;

import java.util.UUID;

import Visitor.SysEntryVisitor;

public interface SysEntry {

    public void accept(SysEntryVisitor visitor);

    public String getName();

    public Boolean isGroup();

    public String getID();

    public String getObservers();

}
