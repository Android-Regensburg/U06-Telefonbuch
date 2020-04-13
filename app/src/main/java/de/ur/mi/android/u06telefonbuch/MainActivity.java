package de.ur.mi.android.u06telefonbuch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

        initViews();
        initButtons();

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
        btnFindFriend = findViewById(R.id.btn_find_friend);

    }

}
