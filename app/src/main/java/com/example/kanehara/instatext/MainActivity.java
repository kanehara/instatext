package com.example.kanehara.instatext;

import android.Manifest;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.telephony.SmsManager;

import java.util.HashMap;
import java.util.Random;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int INSTATEXT_PERM_READ_SMS = 1;
    private static final int INSTATEXT_PERM_READ_CONTACTS = 1;
    private enum MessageType {
        NICE, MEAN
    }
    private MessageType msgType;
    private String message;
    private HashMap<String, String> contactMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNiceButton();
        initMeanButton();
        initSendButton();
        initContacts();
        initRecEditText();
    }

    private void initRecEditText() {
        final ListView contactList = (ListView)findViewById(R.id.contactList);
        contactList.setEnabled(false);
        contactList.setVisibility(View.INVISIBLE);
        final EditText recEditText = (EditText) findViewById(R.id.recEditText);

        recEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactList.setEnabled(true);
                contactList.setVisibility(View.VISIBLE);
                contactList.bringToFront();
            }
        });
        findViewById(R.id.rootLayout).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int diff = findViewById(R.id.rootLayout).getRootView().getHeight()
                            - findViewById(R.id.rootLayout).getHeight();
                System.out.println(diff);
                if (diff > 800) {
                    contactList.setEnabled(true);
                    contactList.setVisibility(View.VISIBLE);
                    contactList.bringToFront();
                }
                else {
                    contactList.setEnabled(false);
                    contactList.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initNiceButton() {
        final Button happyButton = (Button) findViewById(R.id.niceButton);
        happyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showNiceText();
            }
        });
    }
    private void initMeanButton() {
        final Button meanButton = (Button) findViewById(R.id.meanButton);
        meanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showMeanText();
            }
        });
    }

    private void initSendButton() {
        final Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setEnabled(false);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendText();
            }
        });
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
                System.out.println("Row clicked");
            }
        });
    }

    private void setRecipient() {

    }

    private void sendText() {
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        int permCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if (permCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},
                    INSTATEXT_PERM_READ_SMS);
        }
        else {
            String userNum = tMgr.getLine1Number();
            if (userNum.equalsIgnoreCase("+12488913545"))
                sendSMS("7349722262", message);
            else if (userNum.equalsIgnoreCase("17349722262"))
                sendSMS("12488913545", message);
        }
    }

    private void showNiceText() {
        // send a happy text
        Random ran = new Random();
        TextStringSingleton sing = TextStringSingleton.getInstance();
        int seed = ran.nextInt(sing.getNiceTexts().size());
        message = (String) sing.getNiceTexts().toArray()[seed];
        msgType = MessageType.NICE;
        TextView textView = (TextView) findViewById(R.id.messageBox);
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setEnabled(true);
        textView.setText(message);
    }

    private void showMeanText() {
        // send a mean text
        message = genMeanMessage();
        msgType = MessageType.NICE;
        TextView textView = (TextView) findViewById(R.id.messageBox);
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setEnabled(true);
        textView.setText(message);
    }

    private String genMeanMessage() {
        TextStringSingleton sing = TextStringSingleton.getInstance();
        Random ran = new Random();
        String[] prefixArr = sing.getMeanPrefixes().toArray(new String[sing.getMeanPrefixes().size()]);
        String[] adjArr = sing.getMeanAdjs().toArray(new String[sing.getMeanAdjs().size()]);
        String[] noun1Arr = sing.getMeanNouns1().toArray(new String[sing.getMeanNouns1().size()]);
        String[] noun2Arr = sing.getMeanNouns2().toArray(new String[sing.getMeanNouns2().size()]);
        String[] verbArr = sing.getMeanVerbs().toArray(new String[sing.getMeanVerbs().size()]);


        int preSeed = ran.nextInt(prefixArr.length);
        int adj1Seed = ran.nextInt(adjArr.length);
        int noun1Seed = ran.nextInt(noun1Arr.length);
        int noun2Seed = ran.nextInt(noun2Arr.length);
        int verbSeed = ran.nextInt(verbArr.length);

        int formatSeed = ran.nextInt(2);


        StringBuilder result = new StringBuilder();
        result.append(prefixArr[preSeed]);
        result.append(" ");
        if (formatSeed == 0) {
            result.append(adjArr[adj1Seed]);
            result.append(" ");
        }
        else if (formatSeed == 1) {
            result.append(noun1Arr[noun1Seed]);
            result.append(" ");
            result.append(verbArr[verbSeed]);
            result.append(" ");
        }
        result.append(noun2Arr[noun2Seed]);

        return result.toString();
    }

    private void sendSMS(String phoneNum, String mess) {
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNum, null, mess, null, null);
            Toast toast = Toast.makeText(getApplicationContext(), msgType.toString() +" text sent!", Toast.LENGTH_LONG);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if (msgType == MessageType.MEAN)
                v.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.holo_red_dark));
            else if (msgType == MessageType.NICE)
                v.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.holo_purple));
            else
                System.err.println("Invalid message type in sendSMS method");
            toast.show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
