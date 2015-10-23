package com.example.adria.thegang.model;

import java.io.Serializable;

/**
 * Created by adria on 09/10/2015.
 */
public class User implements Serializable {

    private int id;
    private String name;

    private FacebookProfile facebookProfile = new FacebookProfile();
    private GooglePlusProfile googlePlusProfile = new GooglePlusProfile();

    public FacebookProfile getFacebookProfile() {
        return facebookProfile;
    }

    public void setFacebookProfile(FacebookProfile facebookProfile) {
        this.facebookProfile = facebookProfile;
    }

    public GooglePlusProfile getGooglePlusProfile() {
        return googlePlusProfile;
    }

    public void setGooglePlusProfile(GooglePlusProfile googlePlusProfile) {
        this.googlePlusProfile = googlePlusProfile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
