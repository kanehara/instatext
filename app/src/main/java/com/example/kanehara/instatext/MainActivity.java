package com.example.kanehara.instatext;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int INSTATEXT_PERM_READ_SMS = 1;
    private enum MessageType {
        NICE, MEAN
    }
    private MessageType msgType;
    private String message;
    private Recipient recipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecTextView();
        initNiceButton();
        initMeanButton();
        initSendButton();
    }

    public void initRecTextView() {
        Bundle extras = getIntent().getExtras();
        RelativeLayout recLayout = (RelativeLayout) findViewById(R.id.recLayout);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View recView = layoutInflater.inflate(R.layout.contact_row, null);

        if (extras != null) {
            recipient = new Recipient(extras.getString("recMapId"),
                                        extras.getString("recNumber"),
                                        extras.getString("recFullName"),
                                        extras.getBoolean("isFavorite"),
                                        Uri.parse(extras.getString("recPhoto")));
            ((TextView) recView.findViewById(R.id.contactName)).setText(recipient.getFullName());
            ((TextView) recView.findViewById(R.id.contactNumber)).setText(recipient.getPhoneNumber());
            ((ImageView) recView.findViewById(R.id.contactImage)).setImageURI(recipient.getPhoto());
            ((CheckBox) recView.findViewById(R.id.contactFavorite)).setChecked(recipient.getIsFavorite());
        }
        ((CheckBox) recView.findViewById(R.id.contactFavorite)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                recipient.setIsFavorite(isChecked);
                RecipientSelect.setContactFavorite(recipient.getMapId(), isChecked);
                SharedPreferences.Editor settingsEditor = getSharedPreferences(RecipientSelect.PREFS_NAME, 0).edit();
                settingsEditor.putBoolean(recipient.getPhoneNumber(), isChecked);
                settingsEditor.commit();
            }
        });
        recLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recLayout.addView(recView);
    }

    private void initNiceButton() {
        final Button happyButton = (Button) findViewById(R.id.niceButton);
        happyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showNiceText();
                msgType = MessageType.NICE;
            }
        });
    }
    private void initMeanButton() {
        final Button meanButton = (Button) findViewById(R.id.meanButton);
        meanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showMeanText();
                msgType = MessageType.MEAN;
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

    private void sendText() {
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        int permCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        if (permCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},
                    INSTATEXT_PERM_READ_SMS);
        }
        else {
            sendSMS(recipient.getPhoneNumber(), message);
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
            Toast toast = Toast.makeText(getApplicationContext(),
                    msgType.toString() +" text sent to " + recipient.getFullName() + "!",
                    Toast.LENGTH_LONG);
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
