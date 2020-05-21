package de.ur.mi.android.u06telefonbuch.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import de.ur.mi.android.u06telefonbuch.data.Friend;

/**
 * Dieses Interface definiert, wie später aus dem Java-Code auf die Friend-Objekte in der Datenbank
 * zugegriffen werden kann. Die Annotationen verknüpfen die Java-Methoden mit konkreten DB-Operationen.
 * Während einfache Vorgänge (Einfügen, Löschen) direkt über vorgegebene Annoationen definiert werden,
 * müssen für komplexere Vorgänge die entsprechenden SQL-Queries angegeben werden.
 */
@Dao
public interface FriendDao {

    // Einfaches Einfügen des übergebenen Objekts in die Datenbank.
    @Insert
    void addFriend(Friend friends);

    // Sucht ein Friend-Objekt anhand seines Names aus der Datenbank heraus
    @Query("SELECT * FROM friend WHERE name = :friendName")
    Friend getFriendByName(String friendName);
}
