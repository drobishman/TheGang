package com.example.adria.thegang.model;

import java.io.Serializable;

/**
 * Created by adria on 09/10/2015.
 */
public class User implements Serializable {

    private  String mEmail;
    private  String mFirstName;
    private  String mLastName;
    private  String mGender;
    private  boolean isGooglePlus;
    private  boolean isFacebook;

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public boolean isGooglePlus() {
        return isGooglePlus;
    }

    public void setIsGooglePlus(boolean isGooglePlus) {
        this.isGooglePlus = isGooglePlus;
    }

    public boolean isFacebook() {
        return isFacebook;
    }

    public void setIsFacebook(boolean isFacebook) {
        this.isFacebook = isFacebook;
    }
}
