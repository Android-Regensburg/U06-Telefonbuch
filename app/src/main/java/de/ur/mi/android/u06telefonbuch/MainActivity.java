package de.ur.mi.android.u06telefonbuch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "friendsDB";
    private static final String TAG = MainActivity.class.getSimpleName();
    private FriendDatabase friendDatabase;
    private Button btnCreateFriend;
    private Button btnFindFriend;
    private TextView tvSeeName;
    private TextView tvSeePhoneNumber;
    private EditText etFriendName;
    private EditText etFriendPhoneNumber;
    private EditText etFindFriendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFriendDatabase();
        initViews();
        initButtons();

    }

    private void initFriendDatabase(){
        friendDatabase = Room.databaseBuilder(getApplicationContext(),
                FriendDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    private void initViews(){
        tvSeeName = findViewById(R.id.tv_see_name);
        tvSeePhoneNumber = findViewById(R.id.tv_see_phone_number);
        etFriendName = findViewById(R.id.et_friend_name);
        etFriendPhoneNumber = findViewById(R.id.et_friend_phone_number);
        etFindFriendName = findViewById(R.id.et_find_friend_name);
    }

    private void initButtons(){
        btnCreateFriend = findViewById(R.id.btn_create_friend);
        btnCreateFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNewFriend();
            }
        });

        btnFindFriend = findViewById(R.id.btn_find_friend);
        btnFindFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findFriend();
            }
        });
    }

    private void enterNewFriend(){
        new Thread(new Runnable() {
            String friendName = etFriendName.getText().toString();
            String phoneNumber = etFriendPhoneNumber.getText().toString();
            int phoneNumberAsInt = Integer.parseInt(phoneNumber);
            @Override
            public void run() {
                insertNewFriendIntoDB(friendName, phoneNumberAsInt);
            }
        }).start();
    }

    private void insertNewFriendIntoDB(String friendName, int phoneNumber){
        //creates new Friend object and puts the object in the instance of the database created earlier.
        Friend friend = new Friend();
        friend.setFriendName(friendName);
        friend.setPhoneNumber(phoneNumber);
        Log.d(TAG, "run: ");
        friendDatabase.daoAccess().insertOnlySingleFriend(friend);
    }

    private void findFriend(){
        //opens new thread to access database
        new Thread(new Runnable() {
            String friendName = etFindFriendName.getText().toString();
            @Override
            public void run() {
                //retrieves a friend from the database
                Friend friend = friendDatabase.daoAccess().fetchOneFriendByFriendName(friendName);
                if(friend!= null){
                    Log.d(TAG, "run: findFriend: friend not null");
                    //if we have a friend, we change the userinterface
                    fillFriendTextViews(friend, friendName);
                }
                else {
                    Log.d(TAG, "run: findFriend: friend is null");
                }
            }
        }).start();
    }

    private void fillFriendTextViews(Friend friend, final String friendName){
        int phoneNumber = friend.getPhoneNumber();
        final String phoneNumberString = String.valueOf(phoneNumber);
        Log.d("findFriend", "run: "+ phoneNumberString);
        //get back to the ui Thread to change the userInterface according to the data of the friend of the database.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvSeeName.setText("");
                tvSeePhoneNumber.setText("");
                tvSeeName.setText(friendName);
                tvSeePhoneNumber.setText(phoneNumberString);

            }
        });


    }

}
