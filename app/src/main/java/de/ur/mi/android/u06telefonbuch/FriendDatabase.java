package de.ur.mi.android.u06telefonbuch;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Friend.class}, version = 1, exportSchema = false)
public abstract class FriendDatabase extends RoomDatabase {
    public abstract FriendDao daoAccess();
}