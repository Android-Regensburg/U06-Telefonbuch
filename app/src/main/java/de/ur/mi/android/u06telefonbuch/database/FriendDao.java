package de.ur.mi.android.u06telefonbuch.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import de.ur.mi.android.u06telefonbuch.data.Friend;

@Dao
public interface FriendDao {

    @Insert
    void addFriend(Friend friends);

    @Query("SELECT * FROM friend WHERE name = :friendName")
    Friend getFriendByName(String friendName);
}
