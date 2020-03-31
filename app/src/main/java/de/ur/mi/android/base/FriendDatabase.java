package de.ur.mi.android.base;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Friend.class}, version = 1, exportSchema = false)
public abstract class FriendDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}