package src;

public class Tweet {

    private static int timeValue = 0;
    private String username;
    private String tweetStr;
    private int timeStamp;

    public Tweet(String tweetStr, String username) {
        this.tweetStr = tweetStr;
        this.timeStamp = Tweet.timeValue;
        this.username = username;
        Tweet.timeValue++;
    }

    public String getTweet() {
        return this.tweetStr;
    }

    public int getTimeValue() {
        return this.timeStamp;
    }

    public String getUserName() {
        return this.username;
    }

    @Override
    public String toString() {
        return this.username + " " + "(" + this.timeStamp + ") :" + this.tweetStr;
    }

}
