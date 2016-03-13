package com.example.kanehara.instatext;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by kanehara on 3/12/16.
 */
public class RecipientSelect extends AppCompatActivity {

    private HashMap<String, String> contactMap = new HashMap<>();
    private EditText recEditText;

    private static final int INSTATEXT_PERM_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipient_select);
        initRecEditText();
        initContacts();
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
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
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
                    }
                    if (number != null && !contactMap.containsKey(number)) {
                        number = number.replaceAll("\\D+","");
                        contactMap.put(number, name);
                    }
                }
            }
        }
        final HashMapAdapter adapter = new HashMapAdapter(contactMap, this);
        ListView contactListView = (ListView) findViewById(R.id.contactList);
        contactListView.setAdapter(adapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int pos, long id) {
                String recName = ((TextView)view.findViewById(R.id.contactName)).getText().toString();
                String recNumber = ((TextView)view.findViewById(R.id.contactNumber)).getText().toString();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("recName", recName);
                intent.putExtra("recNumber", recNumber);
                startActivity(intent);
            }
        });
    }
}
