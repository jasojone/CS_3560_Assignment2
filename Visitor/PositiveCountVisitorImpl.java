package Visitor;

import src.Tweet;
import src.User;

public class PositiveCountVisitorImpl implements SysEntryVisitor {

    @Override
    public int visit(User user) {
        // TODO Auto-generated method stub

        String[] positive = { "good", "excellent", "great", "nice", "positive", "fun" };

        int result = 0;
        for (Tweet tweet : user.getTweets()) {
            String tweetStr = tweet.getTweet();
            String[] words = tweetStr.split(" ");

            for (String tweetWord : words) {
                Boolean posWordFound = false;
                System.out.println(tweetWord);
                for (String posWord : positive) {

                    System.out.println(posWord);
                    if (tweetWord.equals(posWord)) {
                        posWordFound = true;
                        break;
                    }
                }

                if (posWordFound) {
                    result += 1;
                    break;
                }

            }

        }

        return result;
    }

}
