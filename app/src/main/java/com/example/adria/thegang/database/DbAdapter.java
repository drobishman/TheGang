package com.example.adria.thegang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.adria.thegang.model.FacebookProfile;
import com.example.adria.thegang.model.GooglePlusProfile;
import com.example.adria.thegang.model.User;

/**
 * Created by Alessandro on 08/10/15.
 */
public class DbAdapter {

    private static final String LOG_TAG = DbAdapter.class.getSimpleName();
    // Database fields
    private static final String USER_TABLE = "user";
    private static final String FACEBOOK_PROFILE_TABLE = "facebook_profile";
    private static final String GOOGLE_PLUS_PROFILE_TABLE = "google_plus_profile";



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

    private ContentValues createUserContentValues(User mUser) {
        ContentValues values = new ContentValues();

        values.put("id", mUser.getId());
        values.put("name", mUser.getName());

        return values;
    }

    private ContentValues createFacebookPrifileContentValues(FacebookProfile facebookProfile) {
        ContentValues values = new ContentValues();

        values.put("id", facebookProfile.getId());
        values.put("first_name", facebookProfile.getFirstName());
        values.put("last_name", facebookProfile.getLastName());
        values.put("email", facebookProfile.getEmail());
        values.put("gender", facebookProfile.getGender());

        return values;
    }

    private ContentValues createGooglePlusPrifileContentValues(GooglePlusProfile googlePlusProfile) {
        ContentValues values = new ContentValues();

        values.put("id", googlePlusProfile.getId());
        values.put("family_name", googlePlusProfile.getFamilyName());
        values.put("given_name", googlePlusProfile.getGivenName());
        values.put("email", googlePlusProfile.getEmail());
        values.put("gender", googlePlusProfile.getGender());

        return values;
    }

    public long createUser(User mUser) {
        ContentValues initialValues = createUserContentValues(mUser);
        return database.insertOrThrow(USER_TABLE, null, initialValues);
    }

    public long createFacebookProfile(FacebookProfile facebookProfile) {
        ContentValues initialValues =  createFacebookPrifileContentValues(facebookProfile);
        return database.insertOrThrow(FACEBOOK_PROFILE_TABLE, null, initialValues);
    }

    public long createFacebookProfile(GooglePlusProfile googlePlusProfile) {
        ContentValues initialValues = createGooglePlusPrifileContentValues(googlePlusProfile);
        return database.insertOrThrow(GOOGLE_PLUS_PROFILE_TABLE, null, initialValues);
    }

    //update a profile
    public boolean updateFacebookProfile(long ID, FacebookProfile facebookProfile) {
        ContentValues updateValues = createFacebookPrifileContentValues(facebookProfile);
        return database.update(FACEBOOK_PROFILE_TABLE, updateValues, "id" + "=" + ID, null) > 0;
    }

    //update a profile
    public boolean updateGooglePlusProfile(long ID, GooglePlusProfile googlePlusProfile) {
        ContentValues updateValues = createGooglePlusPrifileContentValues(googlePlusProfile);
        return database.update(GOOGLE_PLUS_PROFILE_TABLE, updateValues, "id" + "=" + ID, null) > 0;
    }

    //update a profile
    public boolean updateUser(long ID, User mUser) {
        ContentValues updateValues = createUserContentValues(mUser);
        return database.update(USER_TABLE, updateValues, "id" + "=" + ID, null) > 0;
    }

    //delete a profile
    public boolean deleteUser() {
        return database.delete(USER_TABLE, null, null) > 0;
    }

    //delete a profile
    public boolean deleteFacebookProfile() {
        return database.delete(FACEBOOK_PROFILE_TABLE, null, null) > 0;
    }

    //delete a profile
    public boolean deleteGooglePlusProfile() {
        return database.delete(GOOGLE_PLUS_PROFILE_TABLE, null, null) > 0;
    }


    //fetch profiles filter by a string
    public boolean hasUser() {
        if(database!=null) {
            Cursor cursor = database.rawQuery("select * from " + USER_TABLE, null);
            cursor.moveToFirst();
            return cursor.getCount()>0;
        }else{
            Log.d(LOG_TAG, "database error!!!");
            return false;
        }
    }

    //fetch profiles filter by a string
    public boolean hasFacebookProfile() {
        if(database!=null) {
            Cursor cursor = database.rawQuery("select * from " + FACEBOOK_PROFILE_TABLE, null);
            cursor.moveToFirst();
            return cursor.getCount()>0;
        }else{
            Log.d(LOG_TAG, "database error!!!");
            return false;
        }
    }

    //fetch profiles filter by a string
    public boolean hasGooglePlusProfile() {
        if(database!=null) {
            Cursor cursor = database.rawQuery("select * from " + GOOGLE_PLUS_PROFILE_TABLE, null);
            cursor.moveToFirst();
            return cursor.getCount()>0;
        }else{
            Log.d(LOG_TAG, "database error!!!");
            return false;
        }
    }

    public FacebookProfile getFacebookProfile(){

        FacebookProfile facebookProfile = new FacebookProfile();

        Cursor cursor = database.rawQuery("select * from "+FACEBOOK_PROFILE_TABLE,null);
        cursor.moveToFirst();
        facebookProfile.setId(cursor.getString(1));
        facebookProfile.setFirstName(cursor.getString(2));
        facebookProfile.setLastName(cursor.getString(3));
        facebookProfile.setEmail(cursor.getString(4));
        facebookProfile.setGender(cursor.getString(5));

        return facebookProfile;

    }

    public GooglePlusProfile getGooglePlusProfile(){

       GooglePlusProfile googlePlusProfile = new GooglePlusProfile();

        Cursor cursor = database.rawQuery("select * from "+FACEBOOK_PROFILE_TABLE,null);
        cursor.moveToFirst();
       googlePlusProfile.setId(cursor.getString(1));
       googlePlusProfile.setFamilyName(cursor.getString(2));
        googlePlusProfile.setGivenName(cursor.getString(3));
        googlePlusProfile.setEmail(cursor.getString(4));
        googlePlusProfile.setGender(cursor.getInt(5));

        return googlePlusProfile;

    }


    public User getUser (){

        User user = new User();

        Cursor cursor = database.rawQuery("select * from "+USER_TABLE,null);

            cursor.moveToFirst();
            user.setId(cursor.getInt(1));
            user.setName(cursor.getString(2));
        if(this.hasFacebookProfile()){
            user.setFacebookProfile(this.getFacebookProfile());
        }
        if(hasGooglePlusProfile()){
            user.setGooglePlusProfile(this.getGooglePlusProfile());
        }
        return user;
    }
}