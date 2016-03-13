package com.example.kanehara.instatext;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.TreeMap;

/**
 * Created by kanehara on 3/12/16.
 */
public class RecipientSelect extends AppCompatActivity {

    private static TreeMap<String, Recipient> contactMap = new TreeMap<>();
    private static TreeMap<String, Recipient> favoriteMap = new TreeMap<>();
    private EditText recEditText;
    private static TreeMapAdapter mainContactsAdapter;
    private static TreeMapAdapter favoritesAdapter;

    private static final int INSTATEXT_PERM_READ_CONTACTS = 1;
    public static final String PREFS_NAME = "InstaTextPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipient_select);
        initRecEditText();
        initContacts();
    }

    public static void setContactFavorite(String mapId, boolean isFavorite) {
        if (isFavorite) {
            if (contactMap.get(mapId) != null) {
                Recipient rec = new Recipient(contactMap.get(mapId));
                contactMap.remove(mapId);
                rec.setIsFavorite(isFavorite);
                favoriteMap.put(mapId, rec);
            }
        }
        else {
            if (favoriteMap.get(mapId) != null) {
                Recipient rec = new Recipient(favoriteMap.get(mapId));
                favoriteMap.remove(mapId);
                rec.setIsFavorite(isFavorite);
                contactMap.put(mapId, rec);
            }
        }
        mainContactsAdapter.notifyDataSetChanged();
        favoritesAdapter.notifyDataSetChanged();
    }

    private void initRecEditText() {
        recEditText = (EditText) findViewById(R.id.recEditText);
    }
    private void initContacts() {
        ContentResolver cr = getContentResolver();
        int permCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (permCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    INSTATEXT_PERM_READ_CONTACTS);
        }
        else {
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String fullName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String number = null;
                    if (Integer.parseInt(cur.getString
                            (cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        pCur.close();
                    }

                    Uri photo = getPhotoForContactId(Long.valueOf(id));
                    if (number != null)
                        number = number.replaceAll("\\D+","");
                    boolean isFavorite = getIsFavoriteForNumber(number);
                    Recipient rec = new Recipient(fullName+number, number, fullName, isFavorite, photo);
                    if (number != null) {
                        if (!isFavorite)
                            contactMap.put(fullName + number, rec);
                        else
                            favoriteMap.put(fullName + number, rec);
                    }
                }
                cur.close();
            }
        }
        mainContactsAdapter = new TreeMapAdapter(contactMap, this);
        favoritesAdapter = new TreeMapAdapter(favoriteMap, this);
        // init main contacts list
        ListView contactListView = (ListView) findViewById(R.id.contactList);
        contactListView.setAdapter(mainContactsAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int pos, long id) {
                Recipient rec = ((TreeMapAdapter.ContactViewHolder)view.getTag()).rec;
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("recMapId", rec.getMapId());
                intent.putExtra("recNumber", rec.getPhoneNumber());
                intent.putExtra("recFullName", rec.getFullName());
                intent.putExtra("isFavorite", rec.getIsFavorite());
                intent.putExtra("recPhoto", rec.getPhoto().toString());

                startActivity(intent);
            }
        });
        // init favorites list

        ListView favoriteListView = (ListView) findViewById(R.id.favoriteList);
        favoriteListView.setAdapter(favoritesAdapter);
        favoriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int pos, long id) {
                Recipient rec = ((TreeMapAdapter.ContactViewHolder) view.getTag()).rec;
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("recMapId", rec.getMapId());
                intent.putExtra("recNumber", rec.getPhoneNumber());
                intent.putExtra("recFullName", rec.getFullName());
                intent.putExtra("isFavorite", rec.getIsFavorite());
                intent.putExtra("recPhoto", rec.getPhoto().toString());

                startActivity(intent);
            }
        });

        ListUtils.setDynamicHeight(contactListView);
        ListUtils.setDynamicHeight(favoriteListView);
    }

    private boolean getIsFavoriteForNumber(String number) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(number, false);
    }

    private Uri getPhotoForContactId(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        return photoUri;
    }
}
