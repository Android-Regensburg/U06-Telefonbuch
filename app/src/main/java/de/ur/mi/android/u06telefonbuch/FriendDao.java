package de.ur.mi.android.u06telefonbuch;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FriendDao {

    @Insert
    void insertOnlySingleFriend(Friend friends);

    @Query("SELECT * FROM friend WHERE friendName = :friendName")
    Friend fetchOneFriendByFriendName(String friendName);
}
