package de.ur.mi.android.u06telefonbuch.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import de.ur.mi.android.u06telefonbuch.data.Friend;

@Database(entities = {Friend.class}, version = 1, exportSchema = false)
public abstract class FriendDatabase extends RoomDatabase {
    public abstract FriendDao friends();
}