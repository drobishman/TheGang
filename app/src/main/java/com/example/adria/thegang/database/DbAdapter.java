package com.example.adria.thegang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adria.thegang.model.User;

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
    public static final String KEY_GOOGLE_PLUS = "google_plus";
    public static final String KEY_FACEBOOK = "facebook";


    private static final String LOG_TAG = DbAdapter.class.getSimpleName();
    // Database fields
    private static final String DATABASE_TABLE = "user";
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

    private ContentValues createContentValues(User mUser) {
        ContentValues values = new ContentValues();
        String first_name = mUser.getmFirstName();
        String last_name = mUser.getmLastName();
        String gender = mUser.getmGender();
        String email = mUser.getmEmail();
        boolean googlePlus = mUser.isGooglePlus();
        boolean facebook = mUser.isFacebook();

        values.put(KEY_FIRST_NAME, first_name);
        values.put(KEY_LAST_NAME, last_name);
        values.put(KEY_GENDER, gender);
        values.put(KEY_EMAIL, email);
        values.put(KEY_GOOGLE_PLUS, googlePlus);
        values.put(KEY_FACEBOOK, facebook);

        return values;
    }

    //
    public long createUser(User mUser) {
        ContentValues initialValues = createContentValues(mUser);
        return database.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }

    //update a profile
    public boolean updateUser(long ID, User mUser) {
        ContentValues updateValues = createContentValues(mUser);
        return database.update(DATABASE_TABLE, updateValues, KEY_ID + "=" + ID, null) > 0;
    }

    //delete a profile
    public boolean deleteUser(long ID) {
        return database.delete(DATABASE_TABLE, KEY_ID + "=" + ID, null) > 0;
    }

    //fetch profiles filter by a string
    public boolean isUser() {
        String[] columns = {KEY_ID};
        Cursor res = database.rawQuery("select * from "+DATABASE_TABLE,null);
        res.moveToFirst();
        Log.d("THEGANG",res.getString(1)+" "+res.getString(2));
        return res.getCount() > 0;
    }
}