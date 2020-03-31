package de.ur.mi.android.base;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySinglefriend(Friend friends);
    @Query("SELECT * FROM friend WHERE friendName = :friendName")

    Friend fetchOneFriendByFriendName(String friendName);
}
