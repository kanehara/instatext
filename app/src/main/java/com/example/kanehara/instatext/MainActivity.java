package com.example.kanehara.instatext;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import java.util.Random;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button happyButton = (Button) findViewById(R.id.niceButton);
        happyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendNiceText();
            }
        });
        final Button meanButton = (Button) findViewById(R.id.meanButton);
        meanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendMeanText();
            }
        });
    }

    private void sendNiceText() {
        // send a happy text
        Random ran = new Random();
        TextStringSingleton sing = TextStringSingleton.getInstance();
        int seed = ran.nextInt(sing.getNiceTexts().size());
        String message = (String) sing.getNiceTexts().toArray()[seed];
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String userNum = tMgr.getLine1Number();
        if (userNum.equalsIgnoreCase("2488913545"))
            sendSMS("7349722262", message, "Nice");
        else if (userNum.equalsIgnoreCase("17349722262"))
            sendSMS("2488913545", message, "Nice");
    }

    private void sendMeanText() {
        // send a mean text
        Random ran = new Random();
        TextStringSingleton sing = TextStringSingleton.getInstance();
        int seed = ran.nextInt(sing.getMeanTexts().size());
        String message = (String) sing.getMeanTexts().toArray()[seed];
        TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String userNum = tMgr.getLine1Number();
        if (userNum.equalsIgnoreCase("2488913545"))
            sendSMS("7349722262", message, "Mean");
        else if (userNum.equalsIgnoreCase("17349722262"))
            sendSMS("2488913545", message, "Mean");
    }


    private void sendSMS(String phoneNum, String mess, String type) {
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNum, null, mess, null, null);
            Toast toast = Toast.makeText(getApplicationContext(), type+" text sent!", Toast.LENGTH_LONG);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if (type == "Mean")
                v.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.holo_red_dark));
            else if (type == "Nice")
                v.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.holo_purple));
            toast.show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
