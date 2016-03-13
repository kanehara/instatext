package com.example.kanehara.instatext;

import android.net.Uri;
import android.view.inputmethod.InputMethodManager;

import java.io.InputStream;

/**
 * Created by kanehara on 3/13/16.
 */
public class Recipient {
    private String mapId;
    private String phoneNumber;
    private String fullName;
    private boolean isFavorite;
    private Uri photo;

    public Recipient(String mapId, String phoneNumber, String fullName, boolean isFavorite, Uri photo) {
        this.mapId = mapId;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.isFavorite = isFavorite;
        this.photo = photo;
    }

    public Recipient(Recipient r) {
        this.mapId = r.mapId;
        this.phoneNumber = r.phoneNumber;
        this.fullName = r.fullName;
        this.isFavorite = r.isFavorite;
        this.photo= r.photo;
    }

    public String getMapId() {
        return mapId;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getFullName() {
        return fullName;
    }
    public boolean getIsFavorite() {
        return isFavorite;
    }
    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
    public Uri getPhoto() {
        return photo;
    }
}
