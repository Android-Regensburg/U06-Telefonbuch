package de.ur.mi.android.u06telefonbuch.database;

import android.app.Activity;

import androidx.room.Room;

import java.util.concurrent.Executors;

import de.ur.mi.android.u06telefonbuch.data.Friend;

public class FriendDatabaseHelper {

    private static final String DATABASE_NAME = "friends-db";
    private FriendDatabase db;

    private final Activity activityContext;

    public FriendDatabaseHelper(Activity activityContext) {
        this.activityContext = activityContext;
        initDatabase();
    }

    private void initDatabase() {
        db = Room.databaseBuilder(activityContext, FriendDatabase.class, DATABASE_NAME).build();
    }

    public void addFriendToDatabase(Friend friend) {
        AddFriendTask task = new AddFriendTask(friend);
        Executors.newSingleThreadExecutor().submit(task);
    }

    public void getFriendByName(String name, FriendQueryResultListener listener) {
        FindFriendTask task = new FindFriendTask(name, listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    private class AddFriendTask implements Runnable {

        private Friend friend;

        public AddFriendTask(Friend friend) {
            this.friend = friend;
        }

        @Override
        public void run() {
            db.friends().addFriend(friend);
        }
    }

    private class FindFriendTask implements Runnable {

        private String friendName;
        private FriendQueryResultListener listener;

        public FindFriendTask(String friendName, FriendQueryResultListener listener) {
            this.friendName = friendName;
            this.listener = listener;
        }

        @Override
        public void run() {
            final Friend friend = db.friends().getFriendByName(friendName);
            // Return to UI-Thread when work is finished
            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listener.onResult(friend);
                }
            });
        }
    }
}
