package src;

public class Tweet {

    private static int timeValue = 0;
    private String tweetStr;
    private int timeStamp;

    public Tweet(String tweetStr) {
        this.tweetStr = tweetStr;
        this.timeStamp = Tweet.timeValue;
        Tweet.timeValue++;
    }

    public String getTweet() {
        return this.tweetStr;
    }

    public int getTimeValue() {
        return this.timeStamp;
    }

    @Override
    public String toString() {
        return this.tweetStr;
    }

}
