package com.example.adria.thegang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Alessandro on 08/10/15.
 */
public class DbAdapter {
    @SuppressWarnings("unused")
    public static final String KEY_ID = "id";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_EMAIL = "email";

    private static final String LOG_TAG = DbAdapter.class.getSimpleName();
    // Database fields
    private static final String DATABASE_TABLE = "profile";
    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DbAdapter(Context context) {
        this.context = context;
    }

    public DbAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private ContentValues createContentValues(String first_name, String last_name, String gender, String email) {
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, first_name);
        values.put(KEY_LAST_NAME, last_name);
        values.put(KEY_GENDER, gender);
        values.put(KEY_EMAIL, email);

        return values;
    }

    //
    public long createProfile(String name, String surname, String gender, String email) {
        ContentValues initialValues = createContentValues(name, surname, gender, email);
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    //update a profile
    public boolean updateProfile(long ID, String name, String surname, String sex, String birth_date) {
        ContentValues updateValues = createContentValues(name, surname, sex, birth_date);
        return database.update(DATABASE_TABLE, updateValues, KEY_ID + "=" + ID, null) > 0;
    }

    //delete a profile
    public boolean deleteProfile(long ID) {
        return database.delete(DATABASE_TABLE, KEY_ID + "=" + ID, null) > 0;
    }

    //fetch profiles filter by a string
    public Cursor fetchProfileByFilter(String filter) {
        Cursor mCursor = database.query(true, DATABASE_TABLE, new String[]{
                        KEY_ID, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_GENDER, KEY_EMAIL},
                KEY_FIRST_NAME + " like '%" + filter + "%'", null, null, null, null, null);
        return mCursor;
    }
}