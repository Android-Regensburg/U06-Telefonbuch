package de.ur.mi.android.u06telefonbuch.database;

import android.app.Activity;
import android.os.Handler;

import androidx.room.Room;

import java.util.concurrent.Executors;

import de.ur.mi.android.u06telefonbuch.data.Friend;

/**
 * Diese Hilfsklasse unterstützt uns dabei, den Zugriff auf die Datenbank möglichst gut vom Rest
 * der Anwendung zu Trennen. Nur in dieser Klasse finden Datenbankoperationen statt. Alle Komponenten,
 * die Informationen aus der Datenbank benötigen oder in die Datenbank schreiben wollem, tun dies über
 * eine Instanz dieser Klasse. FriendDatabaseHelper arbeitet als "Vermittler" zwischen der Room-API
 * und den übrigen Teilen unserer Anwendung.
 */
public class FriendDatabaseHelper {

    private static final String DATABASE_NAME = "friends-db";
    private FriendDatabase db;

    private final Activity activityContext;

    /**
     * Dem Konstruktor der Hilfsklasse wird eine Activity als Parameter übergeben, damit deren
     * Kontext für die Erstellung bzw. Bereitstellung der Datenbank verwendet werden kann.
     */
    public FriendDatabaseHelper(Activity activityContext) {
        this.activityContext = activityContext;
        initDatabase();
    }

    private void initDatabase() {
        db = Room.databaseBuilder(activityContext, FriendDatabase.class, DATABASE_NAME).build();
    }

    /**
     * Wird von anderen Teilen der Anwendung aufgerufen, um das übergebene Friend-Objekt in der
     * Datenbank zu speichern.
     */
    public void addFriendToDatabase(Friend friend) {
        // Erstellt den Task, in dem die Datenbankoperation ausgeführt werden soll
        AddFriendTask task = new AddFriendTask(friend);
        // Führt den Task in einem neuen Thread durch
        Executors.newSingleThreadExecutor().submit(task);
    }

    /**
     * Wird von anderen Teilen der Anwendung aufgerufen, um eine Suche nach dem Friend-Objekt mit
     * dem übergebenen Namen in der Datenbank zu starten. Nach Abschluss der Anfrage wird das Ergebnis
     * an den übergebenen Listener gesendet.
     */
    public void getFriendByName(String name, FriendQueryResultListener listener) {
        FindFriendTask task = new FindFriendTask(name, listener);
        Executors.newSingleThreadExecutor().submit(task);
    }

    /**
     * Diese Runnable kapselt den, nebenläufig durchzuführenden, Vorgang des Hinzufügens eines neuen
     * Datenbankeintrags. Das zuspeichernde Objekt wird dem Task im Konstruktor übergeben.
     */
    private class AddFriendTask implements Runnable {

        private Friend friend;

        public AddFriendTask(Friend friend) {
            this.friend = friend;
        }

        @Override
        public void run() {
            db.friends().addFriend(friend);
        }
    }

    /**
     * Diese Runnable kapselt den, nebenläufig durchzuführenden, Vorgang des Heraussuchens eines
     * Eintrags aus der Datenbank. Der Name, anhand dessen der Eintrag identifiziert werden soll,
     * wird dem Task im Konstruktor übergeben. Zusätzlich wird dem Task ein Listener übergeben, der
     * über das Ergebniss der Suche informiert werden soll.
     */
    private class FindFriendTask implements Runnable {

        private String friendName;
        private FriendQueryResultListener listener;

        public FindFriendTask(String friendName, FriendQueryResultListener listener) {
            this.friendName = friendName;
            this.listener = listener;
        }

        @Override
        public void run() {
            // Ausführen der Datenbankoperation
            final Friend friend = db.friends().getFriendByName(friendName);
            // Wechsel in den UI Thread, nach dem die Datenbankoperation durchgelaufen ist
            activityContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Rückmeldung an den Listener mit dem gefundenen Objekt aus der Datenbank
                    listener.onResult(friend);
                }
            });
        }
    }
}
