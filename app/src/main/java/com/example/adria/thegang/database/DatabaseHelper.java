package com.example.adria.thegang.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Alessandro on 08/10/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "the_gang.db";
    private static final int DATABASE_VERSION = 1;

    // The statement SQL creating the user table
    private static final String USER_TABLE_CREATE = "CREATE TABLE user (" +
            "id integer primary key autoincrement, " +
            "name text not null " +
            ")";
    private static final String FACEBOOK_PROFILE_TABLE_CREATE = "CREATE TABLE facebook_profile (" +
            "id integer primary key, " +
            "first_name text not null, " +
            "last_name text not null, " +
            "email text not null, " +
            "gender text not null " +
            ")";
    private static final String GOOGLE_PLUS_PROFILE_TABLE_CREATE = "CREATE TABLE google_plus_profile (" +
            "id integer primary key, " +
            "family_name text not null, " +
            "given_name text not null, " +
            "email text not null, " +
            "gender integer not null " +
            ")";

    // Costruttore
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Questo metodo viene chiamato durante la creazione del database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(USER_TABLE_CREATE);
        database.execSQL(FACEBOOK_PROFILE_TABLE_CREATE);
        database.execSQL(GOOGLE_PLUS_PROFILE_TABLE_CREATE);
    }

    // Questo metodo viene chiamato durante l'upgrade del database, ad esempio quando viene incrementato il numero di versione
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS user");
        database.execSQL("DROP TABLE IF EXISTS google_plus_profile");
        database.execSQL("DROP TABLE IF EXISTS facebook_profile");
        onCreate(database);
    }
}