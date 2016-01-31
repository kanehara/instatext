package com.example.kanehara.instatext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by kanehara on 1/30/16.
 */
public class HashMapAdapter extends BaseAdapter {
    private HashMap<String, String> hashMap = new HashMap<String, String>();
    private String[] hashMapKeys;
    private LayoutInflater layoutInflater;
    private final Context context;

    private class ContactViewHolder {
        ImageView contactImage;
        TextView contactName;
        TextView contactNumber;
    }

    public HashMapAdapter(HashMap<String, String> hashMap, Context context) {
        this.hashMap = hashMap;
        this.context = context;
        hashMapKeys = getHashMapKeysSortedByVal();
    }
    private String[] getHashMapKeysSortedByVal () {
        String[] result = new String[hashMap.keySet().size()];
        ArrayList<String> hashMapVals = new ArrayList(hashMap.values());
        ArrayList<String> hashMapKeys = new ArrayList(hashMap.keySet());
        Collections.sort(hashMapVals);
        for (int i = 0; i < hashMapVals.size(); ++i) {
            String val = hashMapVals.get(i);
            for (int j = 0; j < hashMapKeys.size(); ++j) {
                String key = hashMapKeys.get(j);
                if (hashMap.get(key) == val) {
                    result[i] = key;
                    hashMapKeys.remove(j);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public int getCount() {
        return hashMap.size();
    }
    @Override
    public Object getItem(int position) {
        return hashMap.get(hashMapKeys[position]);
    }
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        ContactViewHolder holder;
        if (view == null) {
            holder = new ContactViewHolder();
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.contact_row, null);
            holder.contactImage = (ImageView) view.findViewById(R.id.contactImage);
            holder.contactName = (TextView) view.findViewById(R.id.contactName);
            holder.contactNumber = (TextView) view.findViewById(R.id.contactNumber);
            view.setTag(holder);
        }
        else {
            holder = (ContactViewHolder)view.getTag();
        }
        String val = getItem(pos).toString();
        String key = hashMapKeys[pos];
        holder.contactNumber.setText(key);
        holder.contactName.setText(val);
        view.setMinimumHeight(150);
        return view;
    }

}
