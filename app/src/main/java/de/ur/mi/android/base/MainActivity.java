package de.ur.mi.android.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

        initViews();
        initButtons();

    }

    private void initViews(){
        textViewFriendName = (TextView) findViewById(R.id.textview_friend_name);
        textViewFriendPhoneNumber = (TextView) findViewById(R.id.textview_friend_phonenumber);
        textViewSeeName = (TextView) findViewById(R.id.textview_id_see_name);
        textViewSeePhoneNumber = (TextView) findViewById(R.id.textview_id_see_phonenumber);
        editTextFriendName = (EditText) findViewById(R.id.editText_id_friendname);
        editTextFriendPhoneNumber = (EditText) findViewById(R.id.editText_id_friend_phonenumber);
        editTextFindFriend = (EditText) findViewById(R.id.editText_id_findfriend);
    }

    private void initButtons(){
        createFriendButton = (Button) findViewById(R.id.button_id_create_friend);
        //clicklistener

        findFriendButton = (Button) findViewById(R.id.button_id_find_friend);

    }

}
