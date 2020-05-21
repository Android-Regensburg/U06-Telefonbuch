package de.ur.mi.android.u06telefonbuch.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Diese Klasse bildet einen einzelnen Eintrag im Telefonbuch, bzw. eine einzelne Person ab. Die
 * in den Instanzen gespeicherten Informationen sollen in der Datenbank persistiert werden.
 */
@Entity
public class Friend {

    // Für die Verwendung mit der Datebank wird eine zusätzliche Instanzvariable für den Primär-
    // schlüssel ergänzt.
    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String name;
    private int phoneNumber;

    public Friend(String name, int phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}