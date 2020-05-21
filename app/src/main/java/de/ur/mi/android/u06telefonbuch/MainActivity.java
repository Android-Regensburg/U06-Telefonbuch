package de.ur.mi.android.u06telefonbuch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.ur.mi.android.u06telefonbuch.data.Friend;
import de.ur.mi.android.u06telefonbuch.database.FriendDatabaseHelper;
import de.ur.mi.android.u06telefonbuch.database.FriendQueryResultListener;


/**
 * Die Anwendung erlaubt das Speichern von Personen (und Telefonnummern) in einer Datenbank. Die
 * gespeicherten Einträge können anschließend durchsucht werden.
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvSeeName;
    private TextView tvSeePhoneNumber;
    private EditText etFriendName;
    private EditText etFriendPhoneNumber;
    private EditText etFindFriendName;
    // Hilfs-Objekt für den Zugriff auf die Datenbank
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

    /**
     * Liest die eingegeben Informationen (Name und Telefonnummer) aus dem UI aus und speichert diese
     * in der Datenbank.
     */
    private void enterNewFriend() {
        String name = etFriendName.getText().toString();
        String phoneNumber = etFriendPhoneNumber.getText().toString();
        Friend friend = new Friend(name, Integer.parseInt(phoneNumber));
        dbHelper.addFriendToDatabase(friend);
    }

    /**
     * Liest den eingegebe Text aus dem UI aus und stellt eine Suchanfrage an die Datenbank
     */
    private void findFriend() {
        String name = etFindFriendName.getText().toString();
        // Der Methode des Helpers wird ein annonymer Listener übergeben, der über das Ergebniss
        // der (asynchronen) Suche in der Datenbank informiert werden soll.
        dbHelper.getFriendByName(name, new FriendQueryResultListener() {
            // Diese Methode wird von der Hilfsklasse (genauuer, vom Runnable, das in der Hilfsklasse
            // gestartet wird) aufgerufen, sobald die Suche in der Datenbank abgeschlossen ist.
            @Override
            public void onResult(Friend friend) {
                showSearchResults(friend);
            }
        });
    }

    /**
     * Zeigt Name und Telefonnumer des übergebenen Friend-Objekts im User Interface an
     */
    private void showSearchResults(Friend friend) {
        tvSeeName.setText("");
        tvSeePhoneNumber.setText("");
        if(friend != null) {
            tvSeeName.setText(friend.getName());
            tvSeePhoneNumber.setText(String.valueOf(friend.getPhoneNumber()));
        }
    }


}
