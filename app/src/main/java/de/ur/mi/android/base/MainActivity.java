package de.ur.mi.android.base;

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
    private Button createFriendButton;
    private Button findFriendButton;
    private TextView textViewFriendName;
    private TextView textViewFriendPhoneNumber;
    private TextView textViewSeeName;
    private TextView textViewSeePhoneNumber;
    private EditText editTextFriendName;
    private EditText editTextFriendPhoneNumber;
    private EditText editTextFindFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initfriendDatabase();
        initViews();
        initButtons();

    }

    private void initfriendDatabase(){
        //inits the database
        friendDatabase = Room.databaseBuilder(getApplicationContext(),
                FriendDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    private void initViews(){
        //references the variables to the xml elements
        textViewFriendName =  findViewById(R.id.textview_friend_name);
        textViewFriendPhoneNumber = findViewById(R.id.textview_friend_phonenumber);
        textViewSeeName =  findViewById(R.id.textview_id_see_name);
        textViewSeePhoneNumber =  findViewById(R.id.textview_id_see_phonenumber);
        editTextFriendName =  findViewById(R.id.editText_id_friendname);
        editTextFriendPhoneNumber =  findViewById(R.id.editText_id_friend_phonenumber);
        editTextFindFriend =  findViewById(R.id.editText_id_findfriend);
    }

    private void initButtons(){
        //references the buttons to the layout and create onclicklistener
        createFriendButton = findViewById(R.id.button_id_create_friend);
        createFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNewIntro();
            }
        });

        findFriendButton =  findViewById(R.id.button_id_find_friend);
        findFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findfriend();
            }
        });
    }

    private void enterNewIntro(){
        //new thread to put data in the database.
        new Thread(new Runnable() {
            String friendname = editTextFriendName.getText().toString();
            String phonenumber = editTextFriendPhoneNumber.getText().toString();
            int phonenumberAsInt = Integer.parseInt(phonenumber);
            @Override
            public void run() {

                insertNewFriendIntoDB(friendname, phonenumberAsInt);
            }
        }).start();
    }

    private void insertNewFriendIntoDB(String friendname, int phonenumber){
        //creates new Friend object and puts the object in the instance of the database created earlier.
        Friend friend =new Friend();
        friend.setFriendName(friendname);
        friend.setPhoneNumber(phonenumber);
        Log.d(TAG, "run: ");
        friendDatabase.daoAccess().insertOnlySinglefriend(friend);
    }

    private void findfriend(){
        //opens new thread to access database
        new Thread(new Runnable() {
            String friendname = editTextFindFriend.getText().toString();
            @Override
            public void run() {
                //retrieves a friend from the database
                Friend friend = friendDatabase.daoAccess().fetchOneFriendByFriendName(friendname);
                if(friend!= null){
                    Log.d(TAG, "run: findfriend: friend not null");
                    //if we have a friend, we change the userinterface
                    setFriendTextviews(friend, friendname);
                }
                else {
                    Log.d(TAG, "run: findfriend: friend is null");
                }
            }
        }).start();
    }

    private void setFriendTextviews(Friend friend, final String friendname){
        int phonenumber = friend.getPhoneNumber();
        final String phonenumberstring = String.valueOf(phonenumber);
        Log.d("findfriend", "run: "+ phonenumberstring);
        //get back to the ui Thread to change the userInterface according to the data of the friend of the database.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                textViewSeeName.setText("");
                textViewFriendName.setText("");
                textViewSeeName.setText(friendname);
                textViewSeePhoneNumber.setText(phonenumberstring);

            }
        });


    }

}
