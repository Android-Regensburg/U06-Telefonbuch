package de.ur.mi.android.base;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Friend {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int friendId;
    private String friendName;
    private int phoneNumber;

    public Friend() {
    }

    @NonNull
    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(@NonNull int friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}