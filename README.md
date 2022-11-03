# CS 3560 Assignment 2

## Java-based Mini Twitter with a GUI

### Using Java Swing

## Basic functions

'''

1. There is a centralized admin control panel
   -> to create users and user groups
2. A User has:
   -> a unique ID
   -> a list of user IDs that are following this user
   -> a list of user IDs being followed by this user
   -> a news feed list containing a list of Twitter messages
3. A user group has:
   -> a unique ID
   -> can contain any number of users
   -> same user can only be included in one group
   -> a user group can contain other user groups recursively
   -> always a root group called Root to include everything
4. Users can choose to follow other users (not user groups) ny providing the target user ID
   -> Unfollow is not required
5. Users can also post a short Tweet message (a String)
   -> all the followers this message in their news feed lists
   -> user can also see his or her own posted messages
6. Admin control analysis features
   -> 1. output the total number of users
   -> 2. output the total number of groups
   -> 3. output the total number of Tweet messages in all the users's new feed
   -> 4. output the percentage of the positive Tweet messages in all the users' news feed
   -> messages containing positive words, such as good, great,excellent, etc.
   '''
