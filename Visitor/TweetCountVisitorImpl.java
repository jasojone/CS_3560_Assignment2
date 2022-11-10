package Visitor;

import src.User;

public class TweetCountVisitorImpl implements SysEntryVisitor {

    @Override
    public int visit(User user) {
        // TODO Auto-generated method stub
        return user.getNumberOfTweets();
    }

}
