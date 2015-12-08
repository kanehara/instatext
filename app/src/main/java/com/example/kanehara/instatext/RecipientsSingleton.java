package com.example.kanehara.instatext;

import java.util.ArrayList;

/**
 * Created by kanehara on 12/6/15.
 */
public class RecipientsSingleton {
    private static RecipientsSingleton instance = null;
    private ArrayList<String> recipients = new ArrayList<String>();

    private RecipientsSingleton() {
        recipients.add("2488913545");
        recipients.add("7349722262");
    }

    public RecipientsSingleton getInstance() {
        if (this.instance == null)
            this.instance = new RecipientsSingleton();
        return this.instance;
    }
    public ArrayList<String> getRecipients() {
        return this.recipients;
    }
}
