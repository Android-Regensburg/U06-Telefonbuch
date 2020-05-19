package de.ur.mi.android.u06telefonbuch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.ur.mi.android.u06telefonbuch.data.Friend;
import de.ur.mi.android.u06telefonbuch.database.FriendDatabaseHelper;
import de.ur.mi.android.u06telefonbuch.database.FriendQueryResultListener;

public class MainActivity extends AppCompatActivity {

    private TextView tvSeeName;
    private TextView tvSeePhoneNumber;
    private EditText etFriendName;
    private EditText etFriendPhoneNumber;
    private EditText etFindFriendName;

    private FriendDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabase();
        initViews();
        initEvents();
    }

    private void initDatabase() {
        dbHelper = new FriendDatabaseHelper(this);
    }

    private void initViews() {
        tvSeeName = findViewById(R.id.tv_see_name);
        tvSeePhoneNumber = findViewById(R.id.tv_see_phone_number);
        etFriendName = findViewById(R.id.et_friend_name);
        etFriendPhoneNumber = findViewById(R.id.et_friend_phone_number);
        etFindFriendName = findViewById(R.id.et_find_friend_name);
    }

    private void initEvents() {
        Button btnCreateFriend = findViewById(R.id.btn_create_friend);
        Button btnFindFriend = findViewById(R.id.btn_find_friend);
        btnCreateFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNewFriend();
            }
        });
        btnFindFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findFriend();
            }
        });
    }

    private void enterNewFriend() {
        String name = etFriendName.getText().toString();
        String phoneNumber = etFriendPhoneNumber.getText().toString();
        addNewFriendToDatabase(name, Integer.parseInt(phoneNumber));
    }

    private void addNewFriendToDatabase(String friendName, int phoneNumber) {
        Friend friend = new Friend(friendName, phoneNumber);
        dbHelper.addFriendToDatabase(friend);
    }

    private void findFriend() {
        String name = etFindFriendName.getText().toString();
        dbHelper.getFriendByName(name, new FriendQueryResultListener() {
            @Override
            public void onResult(Friend friend) {
                showSearchResults(friend);
            }
        });
    }

    private void showSearchResults(Friend friend) {
        tvSeeName.setText("");
        tvSeePhoneNumber.setText("");
        if(friend != null) {
            tvSeeName.setText(friend.getName());
            tvSeePhoneNumber.setText(String.valueOf(friend.getPhoneNumber()));
        }
    }


}
